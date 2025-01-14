package rBackend;

import java.io.File;
import java.io.IOException;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class RConnector {
	private RConnection c;
	
	public RConnector() {
		startRConnection();
		loadLibraries();
	}
	
	public void loadLibraries() {
		try {
			StringBuilder command = new StringBuilder("");
			command.append("library(dismo)");
			command.append("\n");
			command.append("library(sdm)");
			command.append("\n");
			command.append("library(raster)");
			command.append("\n");
			command.append("library(rgdal)");
			command.append("\n");
			command.append("ph <- readOGR(\"" + System.getProperty("user.dir").replace('\\', '/') + "/PHL_adm_shp/PHL_adm1.shp\")\n");
			c.eval(command.toString());
			System.out.println("Libraries loaded");
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Ui Rserve Exception");
		}
	}
	
	public void startRConnection() {
		try {
			Process pro = null;
			
			ProcessBuilder pb = new ProcessBuilder("RScript", "assets\\InitRserve.txt");
			System.out.println(System.getProperty("user.dir"));
			
			pro = pb.start();
			pro.waitFor();
			pro.destroy();
		}
		catch(IOException io) {
			io.printStackTrace();
			System.out.println("IOException starting Rserve");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Interrupted starting Rserve");
		}
		while(c == null) {
			try {
				c = new RConnection("localhost");
				
				System.out.println("Connected to Rserve");
			} catch (RserveException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("Ui Rserve Exception");
			} 
		}
	}
	public RConnection getRConnection() {
		return this.c;
	}
}
