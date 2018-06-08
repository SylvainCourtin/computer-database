package com.excilys.computerdatabase.ui;

import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.excilys.computerdatabase.dtos.CompanyDTO;
import com.excilys.computerdatabase.dtos.ComputerDTO;
import com.excilys.computerdatabase.mappers.MapperCompany;
import com.excilys.computerdatabase.mappers.MapperComputer;

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
		WebTarget articleById = base.path(URL_COMPUTER).path("{id}").resolveTemplate("id", "10");
		Response response = articleById.request(MediaType.APPLICATION_JSON).get(); //<-- error  InjectionManagerFactory not found.
		
		ComputerDTO computerDTO = (ComputerDTO) response.getEntity();

		System.out.println(MapperComputer.fromParameters(
				computerDTO.getComputerBasicView().getId(), 
				computerDTO.getComputerBasicView().getName(),
				computerDTO.getComputerBasicView().getIntroduced(),
				computerDTO.getComputerBasicView().getDiscontinued(),
				MapperCompany.fromParameters(new CompanyDTO(computerDTO.getCompanyBasicView().getId(),computerDTO.getCompanyBasicView().getName()))));

		
		client.close();
	}
	
	public static void main(String... args) {
		getComputer();
	}
}
