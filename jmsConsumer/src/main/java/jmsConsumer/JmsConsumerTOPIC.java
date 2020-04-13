package jmsConsumer;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsConsumerTOPIC {

	public static void main(String[] args) {

		// saisir le id du consumer
		Scanner scanner = new Scanner(System.in);
		System.out.print("Message Selector: ");
		String messageSelector = scanner.nextLine();

		try {
			// Create connectionFactory ( interface )
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

			// Create Connection
			Connection connection = connectionFactory.createConnection();

			connection.start();

			// Create a session ( False = égale non transaction )
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create the destination ( Topic or Queue). Si elle n'existe pas demande la
			// création
			Destination destination = session.createTopic("myTopic");

			// Create a MessageProducer from the session to the Topic or Queue
			// specifier quel message on souhaite recevoir
			MessageConsumer messageConsumer = session.createConsumer(destination, "code='" + messageSelector + "'");

			// Creer un listener
			MessageListener listener = new MessageListener() {

				public void onMessage(Message message) {
					// TODO Auto-generated method stub
					if (message instanceof TextMessage) {

						try {
							TextMessage textMessage = (TextMessage) message;
							System.out.println("I am Consumer. Message received is : " + textMessage.getText());
						} catch (JMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			};

			messageConsumer.setMessageListener(listener);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
