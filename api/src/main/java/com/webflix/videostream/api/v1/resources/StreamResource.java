package com.webflix.videostream.api.v1.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.webflix.videostream.models.entities.VideoRawDataEntity;
import com.webflix.videostream.services.beans.VideoRawDataBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/streams")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
public class StreamResource {

	@Inject
	private VideoRawDataBean videoRawDataBean;

	@GET
	public Response getStreams() {
		List<VideoRawDataEntity> list = videoRawDataBean.getVideoRawData();
		return Response.ok(list).build();
	}

	@GET
	@Path("/{videoId}")
	public Response getStream(@PathParam("videoId") Integer videoId) {

		List<VideoRawDataEntity> vrde = videoRawDataBean.getVideoRawData(videoId);

		return Response.status(Response.Status.OK).entity(vrde).build();
	}

}
