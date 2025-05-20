package xyz.cursedman.gym_api.exceptions;

public class WorkoutSessionException extends RuntimeException {

	public WorkoutSessionException() {
		super();
	}

	public WorkoutSessionException(String message) {
		super(message);
	}

	public WorkoutSessionException(String message, Throwable cause) {
		super(message, cause);
	}

	public WorkoutSessionException(Throwable cause) {
		super(cause);
	}

	protected WorkoutSessionException(String message, Throwable cause, boolean enableSuppression,
									  boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
