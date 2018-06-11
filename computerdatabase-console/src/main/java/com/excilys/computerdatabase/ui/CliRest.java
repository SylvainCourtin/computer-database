package com.excilys.computerdatabase.ui;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
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
import com.excilys.computerdatabase.utils.DateUtil;

public class CliRest {
	
	private static final Scanner scanner = new Scanner(System.in);
	private static final int PORT = 8080;
	private static final String DEFAULT_URL_CONTEXT = "http://localhost:"+PORT+"/computerdatabase-webservices";
	private static final String URL_COMPUTER = "/computer";
	private static final String URL_COMPANY = "/company";
	
	private static String requestId()
	{
		System.out.println("\n\n\n----------------------------");
		System.out.println("Computer id ?");
		System.out.print("= ");
		return scanner.nextLine();
	}
	
	
	
	public static Computer getComputer(String id)
	{
		Client client = ClientBuilder.newClient();
		Computer computer = null;
		WebTarget base = client.target(DEFAULT_URL_CONTEXT);		
		WebTarget articleById = base.path(URL_COMPUTER).path("{id}").resolveTemplate("id",id);
		Response response = articleById.request(MediaType.APPLICATION_JSON).get();
		if(response.getStatusInfo().equals(Status.OK))
		{
			ComputerDTO computerDTO = response.readEntity(ComputerDTO.class);
			Company company = null;
			if(computerDTO.getCompanyBasicView() != null)
			{
				company = MapperCompany.fromParameters( new CompanyDTO(computerDTO.getCompanyBasicView().getId(),computerDTO.getCompanyBasicView().getName()));
			}
			computer = MapperComputer.fromParameters(
					computerDTO.getComputerBasicView().getId(), 
					computerDTO.getComputerBasicView().getName(),
					computerDTO.getComputerBasicView().getIntroduced(),
					computerDTO.getComputerBasicView().getDiscontinued(),
					company);
			
			System.out.println(computer.toString());
		}
		else if(response.getStatusInfo().equals(Status.NOT_FOUND))
			System.out.println("Id not found");
		else
			System.out.println(response.getStatusInfo());
		
		client.close();
		return computer;
	}
	
	public static void getAllComputers()
	{
		Client client = ClientBuilder.newClient();
		WebTarget base = client.target(DEFAULT_URL_CONTEXT);
		WebTarget articleById = base.path(URL_COMPUTER);
		Response response = articleById.request(MediaType.APPLICATION_JSON).get();
		if(response.getStatusInfo().equals(Status.OK))
		{
			List<ComputerDTO> computerDTOs = response.readEntity(new GenericType<List<ComputerDTO> >(){});
			Company company;
			for (ComputerDTO computerDTO : computerDTOs) {
				company = null;
				if(computerDTO.getCompanyBasicView() != null)
					company = MapperCompany.fromParameters( new CompanyDTO(computerDTO.getCompanyBasicView().getId(),computerDTO.getCompanyBasicView().getName()));

				System.out.println(MapperComputer.fromParameters(
						computerDTO.getComputerBasicView().getId(), 
						computerDTO.getComputerBasicView().getName(),
						computerDTO.getComputerBasicView().getIntroduced(),
						computerDTO.getComputerBasicView().getDiscontinued(),
						company));
			}
			client.close();
		}
		else if(response.getStatusInfo().equals(Status.NOT_FOUND))
			System.out.println("Id not found");
		else
			System.out.println(response.getStatusInfo());
	}
	
	public static void getAllCompanies()
	{
		Client client = ClientBuilder.newClient();
		WebTarget base = client.target(DEFAULT_URL_CONTEXT);
		WebTarget articleById = base.path(URL_COMPANY);
		Response response = articleById.request(MediaType.APPLICATION_JSON).get();
		if(response.getStatusInfo().equals(Status.OK))
		{
			List<CompanyDTO> companiesDTO = response.readEntity(new GenericType<List<CompanyDTO> >(){});
			companiesDTO.forEach(company -> System.out.println(company));
			client.close();
		}
		else if(response.getStatusInfo().equals(Status.NOT_FOUND))
			System.out.println("Id not found");
		else
			System.out.println(response.getStatusInfo());
	}
	
	public static Optional<CompanyDTO> getCompany(Long id)
	{
		Client client = ClientBuilder.newClient();
		WebTarget base = client.target(DEFAULT_URL_CONTEXT);
		WebTarget articleById = base.path(URL_COMPANY).path("{id}").resolveTemplate("id", id.toString());
		Response response = articleById.request(MediaType.APPLICATION_JSON).get();
		if(response.getStatusInfo().equals(Status.OK))
		{
			CompanyDTO companyDTO = response.readEntity(CompanyDTO.class);
			client.close();
			return Optional.of(companyDTO);
		}
		else
		{
			System.out.println(response.getStatusInfo());
			client.close();
			return Optional.empty();
		}	
	}
	
	public static Company requestCompany()
	{
		Company company = null;
		boolean isIDValid = false;
		do {
			try
			{
				String tmp = scanner.nextLine();
				if(!tmp.equals("null"))
				{
					Optional<CompanyDTO> optCompany = getCompany(Long.parseLong(tmp));
					if(!optCompany.isPresent())
					{
						isIDValid = false;
						System.out.println("\n\t This company doesn't exist try again");
					}
					else
					{
						isIDValid = true;
						company = MapperCompany.fromParameters(optCompany.get());
					}
				}
				else
				{
					isIDValid = true;
				}
			}catch (Exception e) {
				System.out.println("\nWrite a number or enter null ");
			}
			
		}while(!isIDValid);
		return company;
	}
	
	public static void addComputer()
	{
		System.out.println("\n-----------Create Computer-----------------");
		System.out.println("Name ?");
		String name = scanner.nextLine().trim();
		
		System.out.println("----------------------------");
		System.out.println("DateIntroduced ?\nexample : dd-MM-yyyy. You can also write null");
		LocalDate dateIntroduced=  DateUtil.requestOkDate();
		
		System.out.println("----------------------------");
		System.out.println("DateDiscontinued ? \n/!\\ Must be more greater than DateIntroduced  \nexample : dd-MM-yyyy. You can also write null");
		LocalDate dateDiscontinued = DateUtil.requestOkDate();
		
		System.out.println("----------------------------");
		System.out.println("Company id ?\nCan be null");
		Company company = requestCompany();
		
		ComputerDTO computerDTO = new ComputerDTO(0, name, dateIntroduced, dateDiscontinued, company);
		
		Client client = ClientBuilder.newClient();
		WebTarget base = client.target(DEFAULT_URL_CONTEXT);
		WebTarget articleById = base.path(URL_COMPUTER);
		Response response = articleById.request(MediaType.APPLICATION_JSON).post(Entity.entity(computerDTO,  MediaType.APPLICATION_JSON));
		if(response.getStatusInfo().equals(Status.OK))
		{
			long id = response.readEntity(Long.class);
			if( id > 0) {
				System.out.println("Successfully added !");
				System.out.println("New ID : "+ id);
			}
			else
				System.out.println("Echec :(");
		}
		else
		{
			System.out.println(response.readEntity(String.class));
			System.out.println(response.getStatusInfo());
		}
		
		client.close();
	}
	
	public static void requestUpdateComputer()
	{
		String id = requestId();
		Computer oldComputer = getComputer(id);
		if( oldComputer != null)
		{
			System.out.println("\n-----------Old Computer-----------------");
			
			System.out.println("New name ?");
			String name = scanner.nextLine().trim();
			
			System.out.println("----------------------------");
			System.out.println("DateIntroduced ?\nexample : dd-MM-yyyy. You can also write null");
			LocalDate dateIntroduced=  DateUtil.requestOkDate();
			
			System.out.println("----------------------------");
			System.out.println("DateDiscontinued ? \n/!\\ Must be more greater than DateIntroduced  \nexample : dd-MM-yyyy. You can also write null");
			LocalDate dateDiscontinued = DateUtil.requestOkDate();
			
			System.out.println("----------------------------");
			System.out.println("Company id ?\nCan be null");
			Company company = requestCompany();
			ComputerDTO computerDTO = new ComputerDTO(0, name, dateIntroduced, dateDiscontinued, company);
			
			Client client = ClientBuilder.newClient();
			WebTarget base = client.target(DEFAULT_URL_CONTEXT);
			WebTarget articleById = base.path(URL_COMPUTER).path("{id}").resolveTemplate("id", id);
			Response response = articleById.request(MediaType.APPLICATION_JSON).put(Entity.entity(computerDTO,  MediaType.APPLICATION_JSON));
			if(response.getStatusInfo().equals(Status.OK))
			{
				System.out.println("Succesfull !");
			}
			else
			{
				System.out.println(response.readEntity(String.class));
				System.out.println(response.getStatusInfo());
			}
			client.close();
			
		}
		else
			System.err.println("Cette ID n'existe pas !");
	}
	
	public static void deleteComputer()
	{
		String id = requestId();
		Client client = ClientBuilder.newClient();
		WebTarget base = client.target(DEFAULT_URL_CONTEXT);
		WebTarget articleById = base.path(URL_COMPUTER).path("{id}").resolveTemplate("id", id);
		Response response = articleById.request(MediaType.APPLICATION_JSON).delete();
		if(response.getStatusInfo().equals(Status.OK))
		{
			System.out.println("Succesfull !");
		}
		else
		{
			System.out.println(response.readEntity(String.class));
			System.out.println(response.getStatusInfo());
		}
		client.close();
	}

	/**
	 * Affiche le menu principal
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 * @throws InputMismatchException
	 * @throws NumberFormatException
	 */
	public static MenuCli displayAction() throws ArrayIndexOutOfBoundsException, InputMismatchException, NumberFormatException
	{
		
		System.out.println("Selection your option ? ");
		System.out.println("----------------------------");
		System.out.println("-1 Get all computers -");
		System.out.println("-2 Get all companies -");
		System.out.println("-3 Show computer details -");
		System.out.println("-4 Add a computer -");
		System.out.println("-5 Update a computer -");
		System.out.println("-6 Delete a computer -");
		System.out.println("-8 Exit -");
		System.out.println("----------------------------");
		
		String sValues = scanner.nextLine();
		Long values = Long.valueOf(sValues);
		if(values > MenuCli.values().length || values < 0)
			throw new ArrayIndexOutOfBoundsException("No menu corresponding at this number");
		else
			return MenuCli.values()[values.intValue()-1];
	}
	//effectue l'affichage et le choix des options
	public static boolean doAction()
	{
		try {
			MenuCli option = displayAction();
			if(option != null)
			{
				switch(option) {
				case GET_ALL_COMPUTERS:
					getAllComputers();
					break;
				case GET_ALL_COMPANIES:
					getAllCompanies();
					break;
				case SHOW_ONE_COMPUTER:
					getComputer(requestId());
					break;
				case ADD_COMPUTER:
					addComputer();
					break;
				case UPDATE_COMPUTER:
					requestUpdateComputer();
					break;
				case DELETE_COMPUTER:
					deleteComputer();
					break;
				case QUIT:
					return false;
				default:
					break;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}catch (InputMismatchException | NumberFormatException e) {
			System.out.println("Oops wrong value try again");
		}
		return true;
	}
	
	
	public static void main(String... args) {
		do {
			
		}while(doAction());
	}
}
