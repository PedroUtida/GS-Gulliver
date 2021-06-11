package br.com.fiap.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.fiap.dao.HotelDAO;
import br.com.fiap.model.Hotel;

@Path("/setups")
public class HotelEndpoint {
	
	private HotelDAO dao = new HotelDAO();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hotel> index(){
		return dao.getAll();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Hotel hotel) {
		if(hotel == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity(hotel).build();
		}
		dao.save(hotel);
		return Response.status(Response.Status.CREATED).entity(hotel).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response show(@PathParam("id") Long id) {
		Hotel hotel;
		try {
			hotel = dao.findById(id);
			if(hotel == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			return Response.status(Response.Status.OK).entity(hotel).build();
		} catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") Long id, Hotel hotel) {
		hotel.setId(id);
		dao.update(hotel);
		return Response.status(Response.Status.OK).entity(hotel).build();

	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response delete(@PathParam("id") Long id, Hotel hotel) {
		if (id == null) {
			return Response.status(Response.Status.BAD_REQUEST).build(); 
		}
		if (hotel == null) {
			return Response.status(Response.Status.BAD_REQUEST).build(); 
		}
		if (dao.findById(id) == null) {
			return Response.status(Response.Status.NOT_FOUND).build(); 
		}
		
		hotel.setId(id);
		
		try {
			dao.delete(hotel);
			return Response.status(Response.Status.OK).entity(hotel).build(); 
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	
	 
}
}
	

