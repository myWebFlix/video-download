package com.webflix.videostream.api.v1.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.webflix.videostream.models.entities.VideoRawDataEntity;
import com.webflix.videostream.services.beans.VideoRawDataBean;
import org.eclipse.microprofile.metrics.Histogram;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Metric;

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

	@Inject
	@Metric(name = "video_watches_histogram")
	Histogram histogram;

	@GET
	public Response getStreams() {
		List<VideoRawDataEntity> list = videoRawDataBean.getVideoRawData();
		return Response.ok(list).build();
	}

	@GET
	@Path("/{videoId}")
	@Counted(name = "number_of_views_counter")
	@Metered(name = "number_of_streams_per_second")
	public Response getStream(@HeaderParam("ID-Token") String idTokenString,
							  @PathParam("videoId") Integer videoId) {

		String userId = videoRawDataBean.manageUser(idTokenString);

		if (userId != null) {

			System.out.println("User ID: " + userId);

			histogram.update(videoId);

			List<VideoRawDataEntity> vrde = videoRawDataBean.getVideoRawData(videoId);

			return Response.status(Response.Status.OK).entity(vrde).build();

		} else {

			return Response.status(Response.Status.UNAUTHORIZED).build();

		}
	}

}
