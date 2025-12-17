package bd;

import model.*;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Chargement de la configuration et création de la SessionFactory.
 * (hibernate.cfg.xml)
 */
public class HibernateUtil
{
	private static final SessionFactory SESSION_FACTORY;

	static
		{
		try	{
            Configuration configuration = new Configuration();
            java.net.URL cfgUrl = Thread.currentThread().getContextClassLoader().getResource("hibernate.cfg.xml");
            if (cfgUrl == null) {
                cfgUrl = HibernateUtil.class.getResource("/hibernate.cfg.xml");
            }
            if (cfgUrl != null) {
                configuration.configure(cfgUrl);
            } else {
                configuration.configure();
            }
            System.out.println("Hibernate Configuration loaded");

			/**
			 * Ajout des classes.
			 * Pour miage.metier.Employe le fichier ressource hbm.xml
			 * attaché est miage/metier/Employe.hbm.xml.
			 */
//			configuration.addClass(miage.metier.Employe.class);

			/**
			 * Entité.
			 */

			configuration.addAnnotatedClass(Utilisateur.class);
			configuration.addAnnotatedClass(Coach.class);
			configuration.addAnnotatedClass(Code.class);
			configuration.addAnnotatedClass(Covoiturage.class);
			configuration.addAnnotatedClass(EnvoyerMessage.class);
			configuration.addAnnotatedClass(EnvoyerMessageId.class);
			configuration.addAnnotatedClass(EtreAbsent.class);
			configuration.addAnnotatedClass(EtrePresent.class);
			configuration.addAnnotatedClass(EtrePresentId.class);
			configuration.addAnnotatedClass(Evenement.class);
			configuration.addAnnotatedClass(ConvocationMatch.class);

			configuration.addAnnotatedClass(Groupe.class);
			configuration.addAnnotatedClass(Joueur.class);
			configuration.addAnnotatedClass(Parent.class);
			configuration.addAnnotatedClass(Secretaire.class);
			configuration.addAnnotatedClass(Code.class);
			configuration.addAnnotatedClass(Reservation.class);



            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate serviceRegistry created");

            SESSION_FACTORY = configuration.buildSessionFactory(serviceRegistry);
			}
		catch (HibernateException ex)
			{
			/*----- Exception -----*/
			System.err.println("Initial SessionFactory creation failed.\n" + ex);
			throw new ExceptionInInitializerError(ex);
			}
		}

	public static SessionFactory getSessionFactory () { return SESSION_FACTORY; }

} /*----- Fin de la classe HibernateUtil -----*/
