package com.webflix.videostream.api.v1.resources;

import com.webflix.videostream.models.entities.VideoRawDataEntity;
import com.webflix.videostream.services.beans.VideoRawDataBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/streams")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StreamResource {

	@Inject
	private VideoRawDataBean videoRawDataBean;

	@GET
	public Response getVideos() {
		List<VideoRawDataEntity> list = videoRawDataBean.getVideoRawData();
		return Response.ok(list).build();
	}

}
