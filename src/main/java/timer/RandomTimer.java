package timer;

import java.util.Random;
import java.util.Vector;

/**
 * The RandomTimer class represents a timer that generates random time intervals based on different distributions.
 * It implements the Timer interface.
 * <p>
 * This class supports four types of random distributions: Poisson, Exponential, Possibilist, and Gaussian.
 * <p>
 * RandomTimer instances can be constructed with different distribution types and parameters.
 *
 * @author Flavien Vernier
 */


public class RandomTimer implements Timer {

	/**
	 * Enumeration representing different random distributions supported by RandomTimer.
	 */
	public static enum randomDistribution {
		/** Poisson distribution. */
		POISSON,
		/** Exponential distribution. */
		EXP,
		/** Possibilist distribution. */
		POSIBILIST,
		/** Gaussian distribution. */
		GAUSSIAN;
	}
	
	//private static String randomDistributionString[] = {"POISSON", "EXP", "POSIBILIST", "GAUSSIAN"};
	
	private Random r = new Random();
	private randomDistribution distribution;
	private double rate;
	private double mean;
	private double lolim;
	private double hilim; 
	//private int width; 

	/**
	 * transform a string into a distribution
	 *
	 * @param distributionName the name of the distribution to create
	 * @return a distribution
	 */
	public static randomDistribution string2Distribution(String distributionName){
		return RandomTimer.randomDistribution.valueOf(RandomTimer.randomDistribution.class, distributionName.toUpperCase());
	}

	/**
	 * transform a distribution into a string
	 *
	 * @param distribution distribution to transform into string
	 * @return a string
	 */
	public static String distribution2String(randomDistribution distribution){
		return distribution.name();
	}

	/**
	 * Construct a random timer object.
	 *
	 * @param distribution the distribution to use
	 * @param param a parameter used to define means
	 * @throws Exception bad Timer
	 */
	public RandomTimer(randomDistribution distribution, double param) throws Exception{
		if(distribution == randomDistribution.EXP ){
			this.distribution = distribution;
			this.rate = param;
			this.mean = 1/param;
			this.lolim = 0;
			this.hilim = Double.POSITIVE_INFINITY;
		}else if(distribution == randomDistribution.POISSON){
			this.distribution = distribution;
			this.rate = Double.NaN;
			this.mean = param;
			this.lolim = 0;
			this.hilim = Double.POSITIVE_INFINITY;
		}else{
			throw new Exception("Bad Timer constructor for selected distribution");
		}
	}

	/**
	 * Construct a random timer object with time limits
	 *
	 * @param distribution the distribution to use
	 * @param lolim lower limit
	 * @param hilim high limit
	 * @throws Exception bad timer
	 */
	public RandomTimer(randomDistribution distribution, int lolim, int hilim) throws Exception{
		if(distribution == randomDistribution.POSIBILIST || distribution == randomDistribution.GAUSSIAN){
			this.distribution = distribution;
			this.mean = lolim + (hilim - lolim)/2;
			this.rate = Double.NaN;
			this.lolim = lolim;
			this.hilim = hilim;
		}else{
			throw new Exception("Bad Timer constructor for selected distribution");
		}
	}

	/**
	 * return the name of the distribution
	 *
	 * @return name
	 */
	public String getDistribution(){
		return this.distribution.name();
	}

	/**
	 * toString method for distribution parameters
	 *
	 * @return a string with information
	 */
	public String getDistributionParam() {
		if(distribution == randomDistribution.EXP ){
			return "rate: " + this.rate;
		}else if(distribution == randomDistribution.POISSON){
			return "mean: " + this.mean;
		}else if(distribution == randomDistribution.POSIBILIST || distribution == randomDistribution.GAUSSIAN){
			return "lolim: " + this.lolim + " hilim: " + this.hilim;
		}
		
		return "null";
	}

	/**
	 * return the mean
	 *
	 * @return mean
	 */
	public double getMean(){
		return this.mean;
	}

	/**
	 * toString method for distribution parameters
	 *
	 * @return a string with information
	 */
	public String toString(){
		String s = this.getDistribution();
		switch (this.distribution){
		case POSIBILIST :
			s += " LoLim:" + this.lolim + " HiLim:" + this.hilim;
			break;
		case POISSON :
			s += " mean:" + this.mean;
			break;
		case EXP :
			s += " rate:" + this.rate;
			break;
		case GAUSSIAN :
			s += " LoLim:" + this.lolim + " HiLim:" + this.hilim;
			break;
		}
		
		return s;
	}

	/**
	 * return next integer
	 *
	 * @return integer
	 */
	@Override
	public Integer next(){
		switch (this.distribution){
		case POSIBILIST :
			return this.nextTimePosibilist();
		case POISSON :
			return this.nextTimePoisson();
		case EXP :
			return this.nextTimeExp();
		case GAUSSIAN :
			return this.nextTimeGaussian();
		}
		return -1; // Theoretically impossible !!!
	}
	
	/*
	 * Equivalent to methodInvocator.RandomTimer#next()
	 * 
	 * @param since has no effect
	 * 
	 * @see methodInvocator.RandomTimer#next(int)
	 */
	/*@Override
	public Integer next(int since){
		return this.next();
	}*/

	/**
	 * Give mean and variance
	 *
	 * @return mean and variance
	 */
	private int nextTimePosibilist(){
	    return (int)this.lolim + (int)(this.r.nextDouble() * (this.hilim - this.lolim));
	}

	/**
	 * Give mean and variance
	 *
	 * @return mean and variance
	 */
	private int nextTimeExp(){
	    return (int)(-Math.log(1.0 - this.r.nextDouble()) / this.rate);
	}


	/**
	 * Give mean and variance
	 *
	 * @return mean and variance
	 */
	private int nextTimePoisson() {
	    
	    double L = Math.exp(-this.mean);
	    int k = 0;
	    double p = 1.0;
	    do {
	        p = p * this.r.nextDouble();
	        k++;
	    } while (p > L);
	    return k - 1;
	}

	/**
	 * Give mean and variance
	 *
	 * @return mean and variance
	 */
	private int nextTimeGaussian(){
		return (int)this.lolim + (int)((this.r.nextGaussian() + 1.0)/2.0 * (this.hilim - this.lolim));
	}

	/**
	 * return if there is a next time
	 *
	 * @return true
	 */
	@Override
	public boolean hasNext() {
		return true;
	}
}
