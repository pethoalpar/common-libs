package com.pethoalpar.entity;

import java.util.List;

import com.pethoalpar.enums.ResponseStatus;

/**
 * @author pethoalpar
 *
 * @param <T>
 */
public class Response<T extends Object> {

	private ResponseStatus status;
	private long executionTime;
	private List<T> data;
	private long total;
	private int page;
	private int totalPageNumber;
	private int size;
	private String error;
	private int errorCode;

	public Response(ResponseStatus status, long executionTime, List<T> data, long total, int page, int totalPageNumber,
			int size, String error, int errorCode) {
		this.status = status;
		this.executionTime = executionTime;
		this.data = data;
		this.total = total;
		this.page = page;
		this.totalPageNumber = totalPageNumber;
		this.size = size;
		this.error = error;
		this.errorCode = errorCode;
	}

	public ResponseStatus getStatus() {
		return this.status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public long getExecutionTime() {
		return this.executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	public List<T> getData() {
		return this.data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public long getTotal() {
		return this.total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPageNumber() {
		return this.totalPageNumber;
	}

	public void setTotalPageNumber(int totalPageNumber) {
		this.totalPageNumber = totalPageNumber;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
