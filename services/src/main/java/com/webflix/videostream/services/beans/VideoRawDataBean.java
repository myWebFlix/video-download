package com.webflix.videostream.services.beans;

import com.webflix.videostream.models.entities.VideoRawDataEntity;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@RequestScoped
public class VideoRawDataBean {

	@PersistenceContext(unitName = "webflix-jpa")
	private EntityManager em;

	public String manageUser(String idTokenString) {
		HttpResponse userAuthResponse = null;

		try {
			HttpClient client = HttpClients.custom().build();
			HttpUriRequest request = RequestBuilder.get()
					.setUri("http://users:8080/v1/auth")
					.setHeader("ID-Token", idTokenString)
					.build();
			userAuthResponse = client.execute(request);

		} catch (Exception e) {
			System.out.println(e);
		}

		if (userAuthResponse != null && userAuthResponse.getStatusLine().getStatusCode() == 200) {

			try {
				HttpEntity entity = userAuthResponse.getEntity();
				String userId = EntityUtils.toString(entity);
				System.out.println("User ID: " + userId);

				return userId;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return null;
	}

	public List<VideoRawDataEntity> getVideoRawData(){

		TypedQuery<VideoRawDataEntity> query = em.createNamedQuery("VideoRawDataEntity.getAll", VideoRawDataEntity.class);

		return query.getResultList();
	}

	public List<VideoRawDataEntity> getVideoRawData(Integer videoId){
		return em.createQuery("SELECT vrd FROM VideoRawDataEntity vrd WHERE vrd.video_id = :video_id", VideoRawDataEntity.class)
				.setParameter("video_id", videoId)
				.getResultList();
	}

	// Transactions

	private void beginTx() {
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
	}

	private void commitTx() {
		if (em.getTransaction().isActive())
			em.getTransaction().commit();
	}

	private void rollbackTx() {
		if (em.getTransaction().isActive())
			em.getTransaction().rollback();
	}

}
