package com.afformed.http.calculator.dto;

import java.util.List;

public class CalcResponse {

	private List<Integer> numbers;
    private List<Integer> windowPrevState;
    private List<Integer> windowCurrState;
    private double average;

    public CalcResponse(List<Integer> numbers, List<Integer> windowPrevState, List<Integer> windowCurrState, double average) {
        this.numbers = numbers;
        this.windowPrevState = windowPrevState;
        this.windowCurrState = windowCurrState;
        this.average = average;
    }

	public List<Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}

	public List<Integer> getWindowPrevState() {
		return windowPrevState;
	}

	public void setWindowPrevState(List<Integer> windowPrevState) {
		this.windowPrevState = windowPrevState;
	}

	public List<Integer> getWindowCurrState() {
		return windowCurrState;
	}

	public void setWindowCurrState(List<Integer> windowCurrState) {
		this.windowCurrState = windowCurrState;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}
    
    
}
