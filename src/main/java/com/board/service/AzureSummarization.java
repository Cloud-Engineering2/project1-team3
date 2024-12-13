package com.board.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.config.AzureConfig;

@Service
public class AzureSummarization {
	@Autowired
	private AzureConfig azureConfig;
	
	public String summarizeContent(String content) {
		String jobId = getJobId(content);
		System.out.println(jobId);
		if (jobId == null) { return null; }
		
		String summary = getSummary(jobId);
		return summary;
	}
	
	private String getSummary(String jobId) {
	    String apiDomain = azureConfig.getApiDomain();
	    String apiKey = azureConfig.getApiKey();
	    String summary = null;

	    try (CloseableHttpClient client = HttpClients.createDefault()) {
	        boolean isCompleted = false;
	        int attempts = 0;

	        while (!isCompleted && attempts < 10) { // 최대 10번 시도
	            // GET 요청 설정
	            HttpGet get = new HttpGet("https://" + apiDomain + "/language/analyze-text/jobs/" + jobId + "?api-version=2023-04-01");
	            get.setHeader("Content-Type", "application/json");
	            get.setHeader("Ocp-Apim-Subscription-Key", apiKey);

	            // 요청 보내기
	            HttpResponse response = client.execute(get);
	            int statusCode = response.getStatusLine().getStatusCode();
	            System.out.println("AzureSummarization GET Response Status Code: " + statusCode);

	            // 응답 내용 출력
	            String responseBody = EntityUtils.toString(response.getEntity());
	            System.out.println("AzureSummarization GET Response Body: " + responseBody);

	            // 응답에서 요약 결과 추출
	            JSONObject jsonResponse = new JSONObject(responseBody);
	            String status = jsonResponse.getString("status");

	            if ("succeeded".equals(status)) {
	                // "tasks" 배열에서 "items" 접근
	                JSONArray items = jsonResponse.getJSONObject("tasks").getJSONArray("items");
	                if (items.length() > 0) {
	                    JSONObject firstItem = items.getJSONObject(0);
	                    JSONArray documents = firstItem.getJSONObject("results").getJSONArray("documents");
	                    if (documents.length() > 0) {
	                        // 첫 번째 문서에서 summaries 접근
	                        JSONObject firstDocument = documents.getJSONObject(0);
	                        JSONArray summaries = firstDocument.getJSONArray("summaries");
	                        if (summaries.length() > 0) {
	                            // 첫 번째 요약 가져오기
	                            summary = summaries.getJSONObject(0).getString("text");
	                        }
	                    }
	                }
	                isCompleted = true; // 작업이 완료됨
	            } else if ("failed".equals(status)) {
	                System.out.println("Job failed");
	                return null; // 작업이 실패했을 경우
	            } else {
	                // 작업이 아직 완료되지 않았으므로 잠시 대기 후 재시도
	                attempts++;
	                Thread.sleep(2000); // 2초 대기
	            }
	        }
	    } catch (IOException | InterruptedException e) {
	        e.printStackTrace();
	    }
	    return summary; // 요약 결과 반환
	}


	private String getJobId(String content) {
		String apiDomain = azureConfig.getApiDomain();
	    String apiKey = azureConfig.getApiKey();
        String jobId = null;
        
        // JSON 요청 데이터
        // 한글 3줄 요약
        String jsonRequest = "{\n" +
                "  \"displayName\": \"Text Abstractive Summarization Task\",\n" +
                "  \"analysisInput\": {\n" +
                "    \"documents\": [\n" +
                "      {\n" +
                "        \"id\": \"1\",\n" +
                "        \"language\": \"ko\",\n" +
                "        \"text\": \"" + content + "\"\n" +  
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"tasks\": [\n" +
                "    {\n" +
                "      \"kind\": \"AbstractiveSummarization\",\n" +
                "      \"taskName\": \"Text Abstractive Summarization Task\",\n" +
                "      \"parameters\": {\n" +
                "          \"sentenceCount\": \"3\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // POST 요청 설정
            HttpPost post = new HttpPost("https://" + apiDomain + "/language/analyze-text/jobs?api-version=2023-04-01");
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Ocp-Apim-Subscription-Key", apiKey);
            post.setEntity(new StringEntity(jsonRequest));

            // 요청 보내기
            HttpResponse response = client.execute(post);
            
            // 응답 상태 코드 확인
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("AzureSummarization POST Response Status Code: " + statusCode);

            // 응답 내용 출력
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println("AzureSummarization POST Response Body: " + responseBody);

            // operation-location 헤더 추출
            String operationLocation = response.getFirstHeader("operation-location") != null ?
                    response.getFirstHeader("operation-location").getValue() : null;

            // jobId 추출
            if (operationLocation != null) {
                jobId = operationLocation.substring(operationLocation.lastIndexOf('/') + 1, operationLocation.indexOf('?'));
                System.out.println("Job ID: " + jobId);
            } else {
                System.out.println("Operation location header not found.");
            }
            
            return jobId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
}
