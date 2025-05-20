package xyz.cursedman.gym_api.exceptions;

public class WorkoutSessionNotFoundException extends WorkoutSessionException {

	public WorkoutSessionNotFoundException(String message) {
		super(message);
	}

	public WorkoutSessionNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public WorkoutSessionNotFoundException(Throwable cause) {
		super(cause);
	}

	public WorkoutSessionNotFoundException(String message, Throwable cause, boolean enableSuppression,
										   boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WorkoutSessionNotFoundException() {
	}
}
