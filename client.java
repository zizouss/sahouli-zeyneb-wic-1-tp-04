package TP3;

	import java.lang.Integer.*;
	import java.rmi.registry.*;
	import java.rmi.*;
	import java.rmi.Naming;
	import java.io.*;


	public class Client 
{

	public static void main(String args[]) throws Exception
	{
	        DataInputStream saisie = new DataInputStream(System.in);

	        int valeur;
	        Integer nombre;
	        int  produit[][], somme[][], mat1[][], mat2[][], lig, col;
	        int INFINI = java.lang.Integer.MAX_VALUE;


	if (args.length != 1)
	   {
	   System.out.println         
	   (" Usage: java Client adresse " );
	   System.exit(1);
	   }

	Service service = (Service) Naming.lookup("//" + args[0] + ":2006/Service");

	if (service != null)
	    {
	     System.out.println(" Connexion etablie au serveur "+args[0]);
	    }
	     else
	        {
	        System.out.println(" Connexion impossible au serveur "+args[0]);
	        }

	    System.out.println("Saisie de la matrice 1: ");
	    System.out.print("Nombre de ligne:");
	    nombre = new Integer(saisie.readLine());
	    lig = nombre.intValue();

	    System.out.print("Nombre de colonne:");
	    nombre = new Integer(saisie.readLine());
	    col = nombre.intValue();
	    mat1 = new int[lig][col];
	    mat1 = lire_matrice(lig,col);
	    
	    System.out.println("Saisie de la matrice 2: ");

	    System.out.print("Nombre de ligne:");
	    nombre = new integer(saisie. readline));
	    lig = nombre.intValue();

	    System.out.print("Nombre de colonne:");
	    nombre = new integer (saisie.readLine());
	    col = nombre.intValue();
	    mat2 = new int[lig][col];
	    mat2 = lire_matrice(lig,col);

	    produit = new int[mat1.length][mat2[0].length];
	    somme  = new int [mat1.length][mat1[0].length];


	    produit = service.produit(mat1, mat2);

	        

	if(produit[0][0] == INFINI)
	   {
	   System.out.println(" Revise votre cour d'Algebre lineaire ... ???");
	   }
	   else
	        if(produit[0][0] != -INFINI)

	           {
	           System.out.println(" Resultat du produit:");
	           affiche_matrice(produit);
	           }

	            else
	                {
	                 System.out.println(" le serveur est charge");
	                 System.out.println(" Reformuler votre requete plus tard... ");
	                 }

	if(produit[0][0] != -INFINI)

	  {
	  somme = service.somme(mat1, mat2);

	  if(somme[0][0] == INFINI)
	     {
	     System.out.println
	          (" Revise votre cour d'Algebre lineaire ... ???");
	     }
	     else
	         {
	          System.out.println(" Resultat de la somme:");
	          affiche_matrice(somme);
	          }
	   }
	}



	public static int[][] lire_matrice(int l, int c) throws Exception
	{
	DataInputStream saisie = new DataInputStream(System.in);

	int t[][], i, j ;

	t = new int [l][c] ;

	for (i = 0 ; i < t.length ; i = i + 1 )
	   for (j = 0 ; j < t[0].length ; j = j + 1 ) 
	    {
	        System.out.print(" [" + (i+1) + "," + (j+1) + "]:") ;
	        String chaine = saisie.readLine();
	        Integer nombre = new Integer(chaine);
	        t[i][j] = nombre.intValue(); 
	        }
	          
	return t ;
	}



	public static void affiche_matrice (int t[][])
	{
	int i, j;
	int space;
	String s;

	System.out.println();
	for (i = 0 ; i < t.length ; i = i + 1 )
	   { 
	   for (j = 0 ; j < t[0].length ; j = j + 1 ) 

	    {
	        s = Integer.toString(t[i][j]);
	        if(j == 0)
	          System.out.print(" | ") ;
	        System.out.print(t[i][j]+ " ") ;
	        for(space = s.length(); space < 7; space++)
	             System.out.print(" ");
	        }
	   System.out.print(" | ") ;
	   System.out.println();
	   }       
	}

}

	
	
