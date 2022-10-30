package dev.ramar.utils.interfaces;


public interface Threadable
{

	// true if started, false if failed to start (already running)
	public boolean start();

	public boolean isRunning();
	
	public void interrupt();

	public void waitForClose();

	public default void stop()
	{
		this.interrupt();
		this.waitForClose();
	}
}