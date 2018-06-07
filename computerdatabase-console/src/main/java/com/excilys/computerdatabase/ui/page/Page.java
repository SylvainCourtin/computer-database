package com.excilys.computerdatabase.ui.page;

import java.util.Scanner;
import com.excilys.computerdatabase.utils.Utils;
/**
 * Cette méthode va effectuer des pages pour la partie CLI, il va permettre de généré des listes
 * trop grande avant de les mettre sur plusieurs "page" via le terminal, 
 * On part du principe d'avoir 10 id par page, la possibilité a l'utilisateur de : 
 * -soit revenir au menu
 * -soit aller à la page suivante
 * -soit indiquer le numéro de page qu'il veut aller
 */
public abstract class Page  {

	protected Scanner scanner = new Scanner(System.in);
	protected long numberOfPages;
	protected int choixPages=0;
	public final int NUMBER_LIST_PER_PAGE = Utils.NUMBER_LIST_PER_PAGE;
	
	public abstract void getInfoPage();
	
	/**
	 * @return return true if the user quit
	 */
	public boolean menuPage()
	{
		String action="";
		int newPages = 1;
		//on demande a l'utilisateur s'il veut quitter ou changer de page

		System.out.println("Number of pages ? Or enter 'quit' for comeback to the Main page");
		action = scanner.nextLine();
		if(action.equals("quit"))
		{
			return true;
		}
		else
		{
			try {
				newPages = Integer.parseInt(action);
				if(newPages > 0 && newPages < numberOfPages+1)
				{
					choixPages=newPages-1;
				}
				else
				{
					System.out.println("This page number doesn't exist ! Try again");
				}
			} catch (Exception e) {
				System.out.println("Oops wrong value try again");
			}
		}

		return false;
	}
	
	public int getChoixPages() {
		return choixPages;
	}

}
