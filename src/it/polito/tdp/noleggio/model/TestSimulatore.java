package it.polito.tdp.noleggio.model;

import java.time.Duration;

public class TestSimulatore {

	public static void main(String[] args) {
		
       Simulatore sim = new Simulatore();
       
    //   sim.setIntervalloArrivoCliente(Duration.ofMinutes(5));
       
		sim.init(15);
		
		sim.run();
		
		System.out.format("%d clienti totali, di cui %d insoddisfatti ", sim.getNumeroclientiTotali(), sim.getNumeroClientiInsoddisfatti());
		
	}

}
