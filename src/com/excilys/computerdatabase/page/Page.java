package com.excilys.computerdatabase.page;

import java.util.Scanner;

/*
 * Cette méthode va effectuer des pages pour la partie CLI, il va permettre de généré des listes
 * trop grande avant de les mettre sur plusieurs "page" via le terminal, 
 * On part du principe d'avoir 10 id par page, la possibilité a l'utilisateur de : 
 * -soit revenir au menu
 * -soit aller à la page suivante
 * -soit indiquer le numéro de page qu'il veut aller
 */
public abstract class Page  {
	
	protected final int NUMBER_LIST_PER_PAGE = 10;

	protected Scanner scanner = new Scanner(System.in);
	protected int numberOfPages;
	
	public abstract void getNumberPage(int page);
	
	/*
	 * Default page 1
	 */
	public void getNumberPage() {
		getNumberPage(1);
	}
	
	public void menuPage()
	{
		String action="";
		int newPages = 1;
		//on affiche la list des companies de la 1er page
		getNumberPage();
		//on demande a l'utilisateur s'il veut quitter ou changer de page
		do {
			System.out.println("Number of pages ? Or enter 'quit' for comeback to the Main page");
			action = scanner.nextLine();
			if(action.equals("quit"))
			{
				return;
			}
			else
			{
				try {
					newPages = Integer.parseInt(action);
					if(newPages > 0 && newPages < numberOfPages+1)
					{
						//on effectue son action
						getNumberPage(newPages);
					}
					else
					{
						System.out.println("This page number doesn't exist ! Try again");
					}
				} catch (Exception e) {
					System.out.println("Oops wrong value try again");
				}
			}
		}while(true);
		
	}
	

}
