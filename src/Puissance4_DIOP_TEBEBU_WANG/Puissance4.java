package Puissance4_DIOP_TEBEBU_WANG;

import java.util.Scanner;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Puissance4 implements Serializable
{
	/*
	 * Les constantes :
	 */
	public static Scanner input = new Scanner(System.in);
	public static int vide = 0;
	public static int joueur_1 = 1;
	public static int joueur_2 = 2;
	
	/*
	 * Les méthodes :
	 */
	
	//Méthode pour initialiser la matrice :
	public static void initialiser (int[][] matrice)
	{
		for(int ligne = 0; ligne < matrice.length; ligne++) {
			for(int colonne = 0; colonne < matrice[0].length; colonne++) {
				matrice[ligne][colonne] = vide;
			}
		}
	}
	
	//Méthode pour convertir la matrice en grille et l'afficher :
	public static void convertirEnGrille(int[][] matrice)
	{
		System.out.println();
		for(int ligne = 0; ligne < matrice.length; ligne++) {
			System.out.print("|");
			for(int colonne = 0; colonne < matrice[ligne].length; colonne++) {
				if (matrice[ligne][colonne] == vide) {
					System.out.print(' ');
				} else if (matrice[ligne][colonne] == joueur_2) {
					System.out.print('O');
				} else {
					System.out.print('X');
				}
				System.out.print('|');
			}
			System.out.println();
		}
	}
	
	//Méthode pour sauvegarder une matrice :
	public static void sauvegarder (int[][] matrice) throws IOException, FileNotFoundException
	{System.out.println("Veuillez nommer la matrice :");
		String nomMatrice = input.next();
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("C:\\Users\\esrom\\Documents\\Technologies de l'information\\Algorithmique\\Puissance4_DIOP_TEBEBU_WANG\\src\\Puissance4_DIOP_TEBEBU_WANG\\" + nomMatrice + ".ser"));
		out.writeObject(matrice);
		out.flush();
		out.close();
	}
	
	//Méthode pour permettre de valider la colonne choisie par le joueur :
	public static boolean jouer(int[][] matrice, int colonneChoisie, int joueur)
	{
		//On vérifie que le numéro de colonne choisie n'est pas faux :
		if (colonneChoisie < 0 || colonneChoisie >= matrice[0].length) {
			return false;
		}
		
		//On vérifie que la colonne est vide :
		if (matrice[0][colonneChoisie] != vide) {
			return false;
		}
		//On commence à la première ligne de la colonne choisie :
		int ligne = matrice.length - 1;
		//Tant que la cellule n'est pas vide, nous montons d'une ligne :
		while ((ligne >= 0) && matrice[ligne][colonneChoisie] != vide) {
			ligne--;
		}
		//Si la ligne trouvée est "plausible", nous remplaçons la cellule par le "jeton" du joueur et validons le coup :
		if (ligne < matrice.length) {
			matrice[ligne][colonneChoisie] = joueur;
			return true;
		} else {
			return false;
		}
	}
	
	
	
	//On compte l'occurence d'un nombre différent de 0 qui est le vide, i.e. 1 pour joueur_1 et 2 pour joueur_2 :
    public static int compter(int[][] matrice, int y, int x, int ydir, int xdir)
    {
    	int somme = 0;
    	int ligne = y;
    	int colonne = x;
    	while(ligne < matrice.length && colonne < matrice[0].length && matrice[ligne][colonne] == matrice[y][x]) {
    		somme++;
    		ligne   += ydir;
    		colonne += xdir;
    	}
    	return somme;
    }
    
    //On vérifie dans quatre directions--horizontale, verticale, diagonale vers le bas et diagonale vers le haut--allant de gauche à droite, sauf pour la direction verticale qui vas bas en haut, s'il y a l'occurence du même nombre différent de 0, i.e. 1 ou 2, plus de trois fois :
    public static boolean gagner(int[][] matrice, int joueur)
    {
    	for(int ligne = 0; ligne < matrice.length; ligne++) {
    		for(int colonne = 0; colonne < matrice[ligne].length; colonne++) {
    			if (matrice[ligne][colonne] == joueur) {
    				if (( colonne <= matrice[ligne].length - 4 && compter(matrice, ligne, colonne, 0, +1) >= 4) //Horizontale, de gauche à droite
    						|| (ligne <= matrice[ligne].length - 4 && compter(matrice, ligne, colonne, +1, 0) >= 4) //Verticale, du haut vers le bas
    						|| (ligne >= matrice.length - 3 && compter(matrice, ligne, colonne, -1, +1) >= 4) //Diagonale descendante, de gauche à droite
    						|| (ligne <= matrice.length - 4 && colonne <= matrice[ligne].length - 4 && compter(matrice, ligne, colonne, +1, +1) >= 4)) { //Diagonale ascendante, de gauche à droite
    					return true;
    				}
    			}
    		}
    	}
    	return false;
    }
    
    //Méthode permettre à deux joueurs de jouer :
    public static void multijoueur(int[][] matrice, int joueur) throws IOException, FileNotFoundException
    {
    	int colonne;
    	boolean coupEstValide, inputEstValide = false;
    	//On reçoit l'input du joueur tant que la colonne choisie n'est pas valide :
    	do {
    		colonne = input.nextInt() - 1; //On accepte un numéro de colonne entre 1 et 7 tandi que la colonne de la matrice va de 0 à 6.
    		coupEstValide = jouer(matrice, colonne, joueur);
    		if (coupEstValide == false) {
    			//Si le jouer à entrer 7867, nous vérifions qu'il veux enregistré le jeux avant de quitter.
    			if (colonne == 7866) {
    				System.out.println("Voulez-vous enregistrez ce jeu ?");
    				char reponse = input.next().charAt(0);
    				if (reponse == 'Y' || reponse == 'y') {
    					sauvegarder(matrice);
    					System.out.println("Vous venez de sauvegarder ce jeu avec succès.");
    					System.out.println("Merci d'avoir jouer notre jeu !");
    					System.exit(0);
    				} else {
    					System.out.println("Merci d'avoir jouer notre jeu !");
    					System.exit(0);
    				}
    			} else {
					System.out.println("Ce coup n'est pas valide.");
				}
    		} else {
    			inputEstValide = true;
    		}
    	} while(coupEstValide == false && inputEstValide == false);
    }
    
    //Méthode pour vérifier si la matrice est pleine :
    public static boolean matriceEstPleine(int[][] matrice)
    {
    	//On parcourt la dernière ligne colonne par colonne pour voir si la matrice est pleine :
    	int ligne = 0;
    	for(int colonne = 0; colonne < matrice[ligne].length; colonne++) {
    		if (matrice[ligne][colonne] == vide) {
    			return false;
    		}
    	}
    	return true;
    }
    
    //Méthode permettant de simuler le lancement d'une pièce équilibrée :
    public static int tirageAuSort ()
    {
    	return (int)((Math.random()*2) + 1);
    }
    
    //Le main pour une partie entre deux joueurs :
    public static void mainMultijoueur() throws IOException, FileNotFoundException, ClassNotFoundException
    {
    	int[][] matrice = new int[6][7];
    	System.out.println("Vous voulez commencer un nouveau jeu ou reprendre un jeu sauvegardé ?\nVeuillez rentrer 1 pour commencer un nouveau jeu et 2 pour continuer un jeu sauvegardé.");
    	int reponse = input.nextInt();
    	if (reponse == 2) {
    		System.out.println("Veuillez rentrez le nom du fichier :");
    		String nomMatrice = input.next();
    		ObjectInputStream in = new ObjectInputStream(new FileInputStream("C:\\Users\\esrom\\Documents\\Technologies de l'information\\Algorithmique\\Puissance4_DIOP_TEBEBU_WANG\\src\\Puissance4_DIOP_TEBEBU_WANG\\" + nomMatrice + ".ser"));
    		matrice = (int[][]) in.readObject();
    		in.close();
    	} else {
        	initialiser(matrice);
    	}
    	convertirEnGrille(matrice);
    	//On "lance une pièce" pour décider qui commence :
    	int joueur = tirageAuSort();
    	
    	boolean victoire;
    	
    	//Reçoit les inputs des deux joueurs tant que il n'y a pas de gagnants et la matrice n'est pas pleine :
    	do {
    		if (joueur == joueur_1) {
    			System.out.println("Joueur 1 :  entrez un numero de colonne entre 1 et 7");
    		} else {
    			System.out.println("Joueur 2 :  entrez un numero de colonne entre 1 et 7");
    		}

    		multijoueur(matrice, joueur);

    		convertirEnGrille(matrice);

    		victoire = gagner(matrice, joueur);
    		
    		//On alterne entre les joueurs :
    		if (joueur == joueur_1) {
        		joueur = joueur_2;
        	} else {
        		joueur = joueur_1;
        	}

    		} while(!victoire && !matriceEstPleine(matrice));
    	
    	//Si il y a un gagnant, on dit qui a gagné [Remarque importante à cause de l'alternation entre les joueurs, le gagnant est différent de la variable joueur] :
    	if (victoire) {
    		if (joueur == joueur_1) {
    			System.out.println("Le joueur n°" + joueur_2 + " a gagne !");
    		} else {
    			System.out.println("Le joueur n°" + joueur_1 + " a gagne !");
    		}
    	} else {
    		//Sinon, on indique que le match est nul.
    		System.out.println("Partie nulle !");
    	}
    }
    
    //Méthode pour vérifier et valider la colonne choisie par l'ordinateur :
    public static boolean jouerContreOrdinateur(int[][] matrice, int colonneChoisie, int joueur)
    {
    	//L'ordinateur est le joueur 2 :
    	if (joueur != joueur_2) {
    		return false;
    	}
    	//Si la colonne choisie est supérieure ou égale à 7, on la rejete :
    	if (colonneChoisie >= matrice[0].length) {
			return false;
		}
		//On commence par la ligne la plus basse :
		int ligne = matrice.length - 1;
		//On vérifie que la colonne choisie n'est pas pleine :
		boolean colonneEstPleine = false;
		//Tant que la colonne choisie n'est pas pleine et la cellule choisie n'est pas vide :
		while ((!colonneEstPleine) && (matrice[ligne][colonneChoisie] != vide)) {
			if (ligne == 0) {
				colonneEstPleine = true;
			} else {
				ligne--;
			}
		}
		//Lorsque la colonne choisie est validée, on la remplace par le "jeton" du joueur :
		if (!colonneEstPleine) {
			matrice[ligne][colonneChoisie] = joueur;
			return true;
		} else {
			return false;
		}
    }
    
    //Méthode pour déterminer la stratégie de l'ordinateur au niveau facile :
    public static void contreOrdinateurFacile (int[][] matrice, int joueur)
    {
    	int colonne;
    	boolean coupEstValide;
    	do {
    		//L'ordinateur choisie une colonne aléatoirement :
    		colonne = (int)(Math.random() * 7);
    		coupEstValide = jouerContreOrdinateur(matrice, colonne, joueur);
    	} while (coupEstValide == false);
    }
    
    //L'affichage pour l'ordinateur du niveau facile :
    public static void mainContreOrdinateurFacile () throws IOException, FileNotFoundException, ClassNotFoundException
    {
    	int[][] matrice = new int[6][7];
    	System.out.println("Vous voulez commencer un nouveau jeu ou reprendre un jeu sauvegardé ?\nVeuillez rentrer 1 pour commencer un nouveau jeu et 2 pour continuer un jeu sauvegardé.");
    	int reponse = input.nextInt();
    	if (reponse == 2) {
    		System.out.println("Veuillez rentrez le nom du fichier :");
    		String nomMatrice = input.next();
    		ObjectInputStream in = new ObjectInputStream(new FileInputStream("C:\\Users\\esrom\\Documents\\Technologies de l'information\\Algorithmique\\Puissance4_DIOP_TEBEBU_WANG\\src\\Puissance4_DIOP_TEBEBU_WANG\\" + nomMatrice + ".ser"));
    		matrice = (int[][]) in.readObject();
    		in.close();
    	} else {
        	initialiser(matrice);
    	}
    	convertirEnGrille(matrice);
    	int joueur = tirageAuSort();
    	boolean victoire;
    	do {
    		if (joueur == joueur_1) {
    			System.out.println("Entrez un numéro de colonne");
    		} else {
    			System.out.println("C'est le tour de l'ordinateur");
    		}
    		
    		if (joueur == joueur_1) {
    			multijoueur(matrice, joueur);
    		} else {
    			contreOrdinateurFacile(matrice, joueur);
    		}
    		convertirEnGrille(matrice);
    		victoire = gagner(matrice, joueur);
    		if (joueur == joueur_1) {
        		joueur = joueur_2;
        	} else {
        		joueur = joueur_1;
        	}
    	} while (!victoire && !matriceEstPleine(matrice));
    	if (victoire) {
    		if (joueur == joueur_1) {
    			System.out.println("Vous avez perdu cette partie.");
    		} else {
    			System.out.println("Vous avez gagnez !");
    		}
    	} else {
    		System.out.println("Partie nulle");
    	}
    }
    
    //On détermine la stratégie de l'ordinateur au niveau moyen :
    public static int jouerContreOrdinateurMoyen(int[][] matrice, int joueur)
    {
    	//On suit les heuristiques suivantes : on favorise les colonnes du centres, puis les angles et enfin les coins.
    	for(int ligne = 0; ligne < matrice.length; ligne++) {
    		for(int colonne = 0; colonne < matrice[ligne].length; colonne++) {
    			if (matrice[matrice.length - 1][3] == vide) {
    				return 3;
    			} else if ((matrice[ligne][2] == vide && matrice[0][2] == vide) || (matrice[ligne][3] == vide && matrice[0][3] == vide) || (matrice[ligne][4] == vide && matrice[0][4] == vide)) {
    				return (int)((Math.random() * (5 - 2)) + 2);
    			}else if (matrice[0][0] == vide || matrice[matrice.length - 1][0] == vide) {
    				return 0;
    			} else if (matrice[0][matrice[ligne].length - 1] == vide || matrice[matrice.length - 1][matrice[ligne].length - 1] == vide) {
    				return matrice[ligne].length - 1;
    			}
    		}
    	}
    	return (int)(Math.random()* 7);
    }
    
    //Méthode permettant de valider et faire les coups de l'ordinateur au niveau moyen :
    public static void contreOrdinateurMoyen (int[][] matrice, int joueur)
    {
    	int colonneChoisie = 3;
    	boolean coupEstValide;
    	do {
    		colonneChoisie = jouerContreOrdinateurMoyen(matrice, joueur);
    		coupEstValide = jouerContreOrdinateur(matrice, colonneChoisie, joueur);
    	} while (coupEstValide == false);
    }
    
    //Le main pour l'ordinateur au niveau moyen :
    public static void mainContreOrdinateurMoyen () throws IOException, FileNotFoundException, ClassNotFoundException
    {
    	int[][] matrice = new int[6][7];
    	System.out.println("Vous voulez commencer un nouveau jeu ou reprendre un jeu sauvegardé ?\nVeuillez rentrer 1 pour commencer un nouveau jeu et 2 pour continuer un jeu sauvegardé.");
    	int reponse = input.nextInt();
    	if (reponse == 2) {
    		System.out.println("Veuillez rentrez le nom du fichier :");
    		String nomMatrice = input.next();
    		ObjectInputStream in = new ObjectInputStream(new FileInputStream("C:\\Users\\esrom\\Documents\\Technologies de l'information\\Algorithmique\\Puissance4_DIOP_TEBEBU_WANG\\src\\Puissance4_DIOP_TEBEBU_WANG\\" + nomMatrice + ".ser"));
    		matrice = (int[][]) in.readObject();
    		in.close();
    	} else {
        	initialiser(matrice);
    	}
    	convertirEnGrille(matrice);
    	int joueur = tirageAuSort();
    	boolean victoire;
    	do {
    		if (joueur == joueur_1) {
    			System.out.println("Entrez un numéro de colonne");
    		} else {
    			System.out.println("C'est le tour de l'ordinateur");
    		}
    		
    		if (joueur == joueur_1) {
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			multijoueur(matrice, joueur);
    		} else {
    			contreOrdinateurMoyen(matrice, joueur);
    		}
    		convertirEnGrille(matrice);
    		victoire = gagner(matrice, joueur);
    		if (joueur == joueur_1) {
        		joueur = joueur_2;
        	} else {
        		joueur = joueur_1;
        	}
    	} while (!victoire && !matriceEstPleine(matrice));
    	if (victoire) {
    		if (joueur == joueur_1) {
    			System.out.println("Vous avez perdu cette partie.");
    		} else {
    			System.out.println("Vous avez gagnez !");
    		}
    	} else {
    		System.out.println("Partie nulle");
    	}
    }
    
    //La stratégie de l'ordinateur au niveau difficile :
    public static int jouerContreOrdinateurDifficile(int[][] matrice, int joueur)
    {
    	//Nous suivons les heuristiques déjà évoquées pour l'ordinateur au niveau moyen mais en plus, l'ordinateur reconnaît les coups assurant des victoires imminentes pour lui et bloque, si possible, les victoires imminentes de son opposant :
    	for (int ligne = 0; ligne < matrice.length; ligne++) {
    		for (int colonne = 0; colonne < matrice[ligne].length; colonne++ ) {
    			if (matrice[matrice.length - 1][3] == vide) {
    				return 3;
    	    	} else if (matrice[ligne][colonne] == joueur_1) { //On vérifie s'il y a des coups assurants des victoires imminentes par le joueur :
    	    		//S'il y a des victoires imminentes horizontales ou diagonales, l'ordinateur choisie la cellule disponible :
    				if ((colonne <= matrice[ligne].length - 4 && ligne >= 0 && compter(matrice, ligne, colonne, 0, +1) == 3 && matrice[ligne][colonne + 4] == vide && matrice[ligne - 1][colonne + 4] != vide)
        					|| (colonne <= matrice[ligne].length - 4 && ligne <= matrice.length - 4 && compter(matrice, ligne, colonne, +1, +1) == 3 && matrice[ligne + 4][colonne + 4] == vide && matrice[ligne + 3][colonne + 4] != vide)
        					|| (ligne >= matrice[ligne].length - 3 && compter(matrice, ligne, colonne, -1, +1) == 3 && matrice[ligne - 4][colonne + 4] == vide)) {
        				return colonne + 4;
        			} else if ((colonne <= matrice[ligne].length - 4 && ligne >= 0 && compter(matrice, ligne, colonne, 0, +1) == 3 && matrice[ligne][colonne - 1] == vide && matrice[ligne - 1][colonne - 1] != vide)
        					|| (colonne <= matrice[ligne].length - 4 && ligne <= matrice.length - 4 && compter(matrice, ligne, colonne, +1, +1) == 3 && matrice[ligne - 1][colonne - 1] == vide && matrice[ligne - 2][colonne - 1] != vide)
        					|| (ligne >= matrice[ligne].length - 3 && compter(matrice, ligne, colonne, -1, +1) == 3 && matrice[ligne + 1][colonne - 1] == vide)) {
        				return colonne - 1;
        			} else if (ligne <= matrice.length - 4 && compter(matrice, ligne, colonne, 1, 0) == 3 && matrice[ligne + 4][colonne] == vide) {
        				//Sinon, l'ordinateur prend la colonne assurant une victoire imminente verticalement :
        				return colonne;
        			} else if (ligne <= matrice.length - 4 && compter(matrice, ligne, colonne, 1, 0) == 3 && matrice[ligne - 1][colonne] == vide) {
        				return colonne;
        			}
    			} else if (matrice[ligne][colonne] == joueur_2) {
    				//S'il y a des victoires imminentes horizontales ou diagonales, l'ordinateur bloque son opposant :
    				if ((colonne <= matrice[ligne].length - 4 && ligne >= 0 && compter(matrice, ligne, colonne, 0, +1) == 3 && matrice[ligne][colonne + 4] == vide && matrice[ligne - 1][colonne + 4] != vide)
        					|| (colonne <= matrice[ligne].length - 4 && ligne <= matrice.length - 4 && compter(matrice, ligne, colonne, +1, +1) == 3 && matrice[ligne + 4][colonne + 4] == vide && matrice[ligne + 3][colonne + 4] != vide)
        					|| (ligne >= matrice[ligne].length - 3 && compter(matrice, ligne, colonne, -1, +1) == 3 && matrice[ligne - 4][colonne + 4] == vide)) {
        				return colonne + 4;
        			} else if ((colonne <= matrice[ligne].length - 4 && ligne >= 0 && compter(matrice, ligne, colonne, 0, +1) == 3 && matrice[ligne][colonne - 1] == vide && matrice[ligne - 1][colonne - 1] != vide)
        					|| (colonne <= matrice[ligne].length - 4 && ligne <= matrice.length - 4 && compter(matrice, ligne, colonne, +1, +1) == 3 && matrice[ligne - 1][colonne - 1] == vide && matrice[ligne - 2][colonne - 1] != vide)
        					|| (ligne >= matrice[ligne].length - 3 && compter(matrice, ligne, colonne, -1, +1) == 3 && matrice[ligne + 1][colonne - 1] == vide)) {
        				return colonne - 1;
        			} else if (ligne <= matrice.length - 4 && compter(matrice, ligne, colonne, 1, 0) == 3 && matrice[ligne + 4][colonne] == vide) {
        				//Sinon, l'ordinateur bloque la colonne assurant une victoire imminente verticalement :
        				return colonne;
        			} else if (ligne <= matrice.length - 4 && compter(matrice, ligne, colonne, 1, 0) == 3 && matrice[ligne - 1][colonne] == vide) {
        				return colonne;
        			}
    			} else {
    				//Idem que ordinateur moyen :
    				if ((matrice[ligne][2] == vide && matrice[0][2] == vide) || (matrice[ligne][3] == vide && matrice[0][3] == vide) || (matrice[ligne][4] == vide && matrice[0][4] == vide)) {
        				return (int)((Math.random() * (5 - 2)) + 2);
        			}else if (matrice[0][0] == vide && matrice[matrice.length - 1][0] == vide) {
        				return 0;
        			} else if (matrice[0][matrice[ligne].length - 1] == vide && matrice[matrice.length - 1][matrice[ligne].length - 1] == vide) {
        				return matrice[ligne].length - 1;
        			} else {
        				return (int)(Math.random() * 7);
        			}
    			}
    		}
    	}
		return (int)(Math.random() * 7);
    }
    
    //Méthode pour valider et réaliser les coups de l'ordinateur au niveau difficile :
    public static void contreOrdinateurDifficile (int[][] matrice, int joueur)
    {
    	int colonneChoisie = 3;
    	boolean coupEstValide;
    	do {
    		colonneChoisie = jouerContreOrdinateurDifficile(matrice, joueur);
    		coupEstValide = jouerContreOrdinateur(matrice, colonneChoisie, joueur);
    	} while (coupEstValide == false);
    }
    
    //Le main de l'ordinateur au niveau difficile :
    public static void mainContreOrdinateurDifficile() throws IOException, FileNotFoundException, ClassNotFoundException
    {
    	int[][] matrice = new int[6][7];
    	System.out.println("Vous voulez commencer un nouveau jeu ou reprendre un jeu sauvegardé ?\nVeuillez rentrer 1 pour commencer un nouveau jeu et 2 pour continuer un jeu sauvegardé.");
    	int reponse = input.nextInt();
    	if (reponse == 2) {
    		System.out.println("Veuillez rentrez le nom du fichier :");
    		String nomMatrice = input.next();
    		ObjectInputStream in = new ObjectInputStream(new FileInputStream("C:\\Users\\esrom\\Documents\\Technologies de l'information\\Algorithmique\\Puissance4_DIOP_TEBEBU_WANG\\src\\Puissance4_DIOP_TEBEBU_WANG\\" + nomMatrice + ".ser"));
    		matrice = (int[][]) in.readObject();
    		in.close();
    	} else {
        	initialiser(matrice);
    	}
    	convertirEnGrille(matrice);
    	int joueur = tirageAuSort();
    	boolean victoire;
    	do {
    		if (joueur == joueur_1) {
    			System.out.println("Entrez un numéro de colonne");
    		} else {
    			System.out.println("C'est le tour de l'ordinateur");
    		}
    		
    		if (joueur == joueur_1) {
    			multijoueur(matrice, joueur);
    		} else {
    			contreOrdinateurDifficile(matrice, joueur);
    		}
    		convertirEnGrille(matrice);
    		victoire = gagner(matrice, joueur);
    		if (joueur == joueur_1) {
        		joueur = joueur_2;
        	} else {
        		joueur = joueur_1;
        	}
    	} while (!victoire && !matriceEstPleine(matrice));
    	if (victoire) {
    		if (joueur == joueur_1) {
    			System.out.println("Vous avez perdu cette partie.");
    		} else {
    			System.out.println("Vous avez gagnez !");
    		}
    	} else {
    		System.out.println("Partie nulle");
    	}
    }
    
    /*
     * Le main :
     */
    
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException
    {
    	System.out.println("Bienvenu à Puissance4 !");
    	System.out.println("Voulez vous jouer contre l'ordinateur ?\nEntrez Y pour dire oui ou N pour dire non");
    	System.out.println("Pour arrêter une fois que vous avez commencer un jeu, veuillez rentrer 7867 lorsqu'on vous demande d'entrer un numéro de colonne et vous pourriez sauvegarder le jeu.");
    	boolean reponseEstCorrect = false;
    	while (!reponseEstCorrect) {
    		char reponse = input.next().charAt(0);
        	if (reponse == 'Y' || reponse == 'y') {
        		System.out.println("Quel est le niveau :\nEntrez 1 pour facile, 2 pour moyen et 3 pour difficile.");
        		reponseEstCorrect = true;
        		boolean niveauEstCorrect = false;
        		while (!niveauEstCorrect) {
        			int niveau = Integer.parseInt(input.next());
            		if (niveau == 1) {
            			System.out.println("Jeu contre ordinateur facile :");
        				mainContreOrdinateurFacile();
            			niveauEstCorrect = true;
            		} else if (niveau == 2) {
            			System.out.println("Jeu contre ordinateur moyen :");
            			mainContreOrdinateurMoyen();
            			niveauEstCorrect = true;
            		} else if (niveau == 3) {
            			System.out.println("Jeu contre ordinateur difficile :");
            			mainContreOrdinateurDifficile();
            			niveauEstCorrect = true;
            		} else {
            			System.out.println("Veuillez choisir un nombre entre 1 et 3");
            		}
        		}
        	} else if (reponse == 'N' || reponse == 'n') {
            	System.out.println("Multijoueur :");
            	mainMultijoueur();
            	reponseEstCorrect = true;
        	}
    	}
    }
}