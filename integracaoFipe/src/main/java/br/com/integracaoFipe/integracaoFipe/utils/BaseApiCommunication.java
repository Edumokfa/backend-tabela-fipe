package br.com.integracaoFipe.integracaoFipe.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class BaseApiCommunication implements Serializable {

    @Value("${api.external.url}")
    private String apiExternalUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public List<Object> getListDataFromUrl(String endPathUrl, ParameterizedTypeReference<List<Object>> responseType) {
        ResponseEntity<List<Object>> response = restTemplate.exchange(apiExternalUrl + endPathUrl, HttpMethod.GET, null, responseType);
        return response.getBody();
    }

    public Map<String, List<Object>> getMapListDataFromUrl(String endPathUrl, ParameterizedTypeReference<Map<String, List<Object>>> responseType) {
        ResponseEntity<Map<String, List<Object>>> response = restTemplate.exchange(apiExternalUrl + endPathUrl, HttpMethod.GET, null, responseType);
        return response.getBody();
    }

    public Object getDataFromUrl(String endPathUrl, Class responseType) {
        Object response = restTemplate.getForObject(apiExternalUrl + endPathUrl, responseType);
        return response;
    }

    public void setApiExternalUrl(String apiExternalUrl) {
        this.apiExternalUrl = apiExternalUrl;
    }
}
