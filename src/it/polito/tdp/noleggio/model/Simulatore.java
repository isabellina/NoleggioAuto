package it.polito.tdp.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import it.polito.tdp.noleggio.model.Evento.TipoEvento;

public class Simulatore {
	
	private PriorityQueue<Evento> queue = new PriorityQueue<>() ;   //CODA PRIORITARIA SU CUI SI REGGERà L'INTERA SIMULAZIONE

	/**
	 * SIMULATORE DEVE MANTENERE LO STATO DEL MONDO CIOè DEL SISTEMA CHE ABBIAMO MODELLATO, CHE COSA VEDO , CHE COSA MI INTERESSA VEDERE
	 * E DOVRà TENERE CONTO DI ALCUNI PARAMENTRI (es ogni quanti minuti arriva un cliente ), serve al simulatore per decidere gli eventi
	 * E DELLE STATISTICHE RACCONLTE
	 */
	
	private int autoTotali;
	private int autoDisponibili;
	
	//con questi due dati so dirti in che stato si trova il mio noleggiatore d'auto
	
	
	
	private LocalTime oraInizio = LocalTime.of(8, 0);  //of costruisce il nuovo oggetto non c'è bisogno del new
	private LocalTime oraFine = LocalTime.of(20, 0);
	private Duration intervalloArrivoCliente = Duration.ofMinutes(10);
	private List<Duration> durataNoleggio ;
	
	
	
	private int numeroclientiTotali;
	private int numeroClientiInsoddisfatti;
	
	//variabili interne
	private Random rand = new Random();
	
	public Simulatore() {
		durataNoleggio = new ArrayList<Duration>();
		durataNoleggio.add(Duration.ofHours(1));
		durataNoleggio.add(Duration.ofHours(2));
		durataNoleggio.add(Duration.ofHours(3));
	}
	
	
	
	
	
	/**
	 * sulla base di questi variabili il simulatore avrà due metodi principali uno per inizializzare la simulazione per preparare
	 * tutte le variabili e uno per eseguire la silulazione vera e propria
	 */
	
	public void init(int autoTotali) {
		this.autoTotali = autoTotali;
		this.autoDisponibili = autoTotali;
		this.numeroclientiTotali = 0;
		this.numeroClientiInsoddisfatti = 0;
		
		this.queue.clear();
		
		//carico eventi iniziali se difficile meglio fare metodo separato preload
		
		//arriva un cliente ogni 10 minuti dalle ora inizio
		
		for(LocalTime ora = oraInizio; ora.isBefore(oraFine);  //itero fino a che l'ora è minore dell'ora di fine
				ora = ora.plus(intervalloArrivoCliente)) {  //incremento sommandogli ogni volta l'intervallo di tempo
			queue.add(new Evento(ora, TipoEvento.CLIENTE_ARRIVA));  //i clienti andranno inseriti in ordine di tempo crescente
		
		}
		
		
	}
	
	public void run() {  //run verrà chiamato sempre dopo init perchè init prepara le variabili
		
		while(!queue.isEmpty()) {
			Evento ev = queue.poll(); // prende l'evento lo legge e lo toglie dalla lista
			System.out.println(ev);
			switch(ev.getTipo()) {
				case CLIENTE_ARRIVA:
					this.numeroclientiTotali++;
					if(this.autoDisponibili==0) {
						this.numeroClientiInsoddisfatti++;
					}
					else {
						//noleggio un'auto al cliente
						this.autoDisponibili--;
						
						int i = rand.nextInt(durataNoleggio.size());
					   Duration noleggio = durataNoleggio.get(i);
					   LocalTime rientro = ev.getTempo().plus(noleggio); // ora di adesso più la durata del noleggio
					
					queue.add(new Evento(rientro, TipoEvento.AUTO_RESTITUITA));
					
					
					}
				break;
				
				case AUTO_RESTITUITA:
					this.autoDisponibili++;
					break;
			
			}
		}
	
	}





	public LocalTime getOraInizio() {
		return oraInizio;
	}





	public void setOraInizio(LocalTime oraInizio) {
		this.oraInizio = oraInizio;
	}





	public LocalTime getOraFine() {
		return oraFine;
	}





	public void setOraFine(LocalTime oraFine) {
		this.oraFine = oraFine;
	}





	public Duration getIntervalloArrivoCliente() {
		return intervalloArrivoCliente;
	}





	public void setIntervalloArrivoCliente(Duration intervalloArrivoCliente) {
		this.intervalloArrivoCliente = intervalloArrivoCliente;
	}





	public List<Duration> getDurataNoleggio() {
		return durataNoleggio;
	}





	public void setDurataNoleggio(List<Duration> durataNoleggio) {
		this.durataNoleggio = durataNoleggio;
	}





	public int getAutoTotali() {
		return autoTotali;
	}





	public int getAutoDisponibili() {
		return autoDisponibili;
	}





	public int getNumeroclientiTotali() {
		return numeroclientiTotali;
	}





	public int getNumeroClientiInsoddisfatti() {
		return numeroClientiInsoddisfatti;
	}
	
	/**
	 * evento di restituzione non lo potevo creare nel metodo init perchè lì non sapevo se all'arrivo di un cliente
	 * ci sarebbero state auto disponibili
	 */
	
	
	
	
	
}


