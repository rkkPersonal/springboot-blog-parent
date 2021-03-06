package org.xr.happy.exception;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

/**
 * @author Steven
 */
public class RestErrorHandler implements ResponseErrorHandler {


    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        int rawStatusCode = response.getRawStatusCode();
        HttpStatus statusCode = HttpStatus.resolve(rawStatusCode);
        HttpStatus.Series series = HttpStatus.Series.resolve(rawStatusCode);
        return (statusCode.isError() || series == HttpStatus.Series.CLIENT_ERROR || series == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {

    }
}
