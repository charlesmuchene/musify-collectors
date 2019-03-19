package dev.cstv.collector.service.impl;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import dev.cstv.collector.rest.RestHttpHeader;
import dev.cstv.collector.service.SongService;
import dev.cstv.musify.domain.Song;

@Component
public class SongServiceImpl implements SongService {

	private Logger LOG = Logger.getLogger(SongServiceImpl.class.getName());

	private static String BASE_URL = "http://dummy.restapiexample.com/api/v1/";

	String baseUrl = "http://localhost:8080/JerseyRestSecurity/songs";
	String baseUrlExtended = baseUrl + "/";

	@Autowired
	RestHttpHeader restHttpHeader;
	

	public static void main(String[] args) {
	}

	public Song read(Integer index) {
		RestTemplate restTemplate = restHttpHeader.getRestTemplate();
		return (restTemplate
				.exchange(baseUrlExtended + index, HttpMethod.GET, restHttpHeader.getHttpEntity(), Song.class)
				.getBody());
		// Returns Song in Body HTTP Message
	}

	public void create(Song song) {
		// TODO call webservice
		System.out.println("calling REST: " + song.getTitle());
		RestTemplate restTemplate = restHttpHeader.getRestTemplate();
		// HTTPEntity - SEND Headers & Body
		HttpEntity<Song> httpEntity = new HttpEntity<Song>(song, restHttpHeader.getHttpHeaders());
		restTemplate.postForObject(baseUrl, httpEntity, Song.class);
		// Song.class - Response type
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(BASE_URL).build();
	}

	public List<Song> read() {

		RestTemplate restTemplate = restHttpHeader.getRestTemplate();
		return Arrays.asList(
				restTemplate.exchange(baseUrl, HttpMethod.GET, restHttpHeader.getHttpEntity(), Song[].class).getBody());
		// restHttpHeader.getHttpEntity() - get HTTP headers [Authentication; JSON
		// ACCEPT]
	}

}