package com.excilys.computerdatabase.ui;

import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.service.ServiceComputer;

public class ViewComputer {
	
	private ServiceComputer serviceComputer;
	public ViewComputer() {
		serviceComputer = new ServiceComputer();
	}
	
	public void showList()
	{
		for (Computer c : serviceComputer.getComputers()) {
			System.out.println(c.toString());
		}
	}
	
	public void showOneComputer(long id)
	{
		System.out.println(serviceComputer.getDetailsComputer(id).toString());
	}
	
	public ServiceComputer getServiceComputer() {
		return serviceComputer;
	}
}
