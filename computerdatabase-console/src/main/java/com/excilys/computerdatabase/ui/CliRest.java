package com.excilys.computerdatabase.ui;

import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.excilys.computerdatabase.dtos.CompanyDTO;
import com.excilys.computerdatabase.dtos.ComputerDTO;
import com.excilys.computerdatabase.mappers.MapperCompany;
import com.excilys.computerdatabase.mappers.MapperComputer;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;

public class CliRest {
	
	private static final Scanner scanner = new Scanner(System.in);
	private static final int PORT = 8080;
	private static final String DEFAULT_URL_CONTEXT = "http://localhost:"+PORT+"/computerdatabase-webservices";
	private static final String URL_COMPUTER = "/computer";
	private static final String URL_COMPANY = "/company";
	
	
	
	public static void getComputer()
	{
		Client client = ClientBuilder.newClient();
		WebTarget base = client.target(DEFAULT_URL_CONTEXT);
		WebTarget articleById = base.path(URL_COMPUTER).path("{id}").resolveTemplate("id", "70");
		Response response = articleById.request(MediaType.APPLICATION_JSON).get();
		if(response.getStatusInfo().equals(Status.OK))
		{
			ComputerDTO computerDTO = response.readEntity(ComputerDTO.class);
			Company company = null;
			if(computerDTO.getCompanyBasicView() != null)
				company = MapperCompany.fromParameters( new CompanyDTO(computerDTO.getCompanyBasicView().getId(),computerDTO.getCompanyBasicView().getName()));

			System.out.println(MapperComputer.fromParameters(
					computerDTO.getComputerBasicView().getId(), 
					computerDTO.getComputerBasicView().getName(),
					computerDTO.getComputerBasicView().getIntroduced(),
					computerDTO.getComputerBasicView().getDiscontinued(),
					company));

			
			client.close();
		}
		else
			System.out.println("error " + response.getStatusInfo());
		
	}
	
	public static void getCompany()
	{
		Client client = ClientBuilder.newClient();
		WebTarget base = client.target(DEFAULT_URL_CONTEXT);
		WebTarget articleById = base.path(URL_COMPANY).path("{id}").resolveTemplate("id", "10");
		Response response = articleById.request(MediaType.APPLICATION_JSON).get();
		if(response.getStatusInfo().equals(Status.OK))
		{
			System.out.println(
					MapperCompany.fromParameters(response.readEntity(CompanyDTO.class)).toString());
		}
		else
			System.out.println("error " + response.getStatusInfo());

		
		client.close();
	}
	
	public static void main(String... args) {
		getComputer();
	}
}
