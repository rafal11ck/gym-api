package xyz.cursedman.gym_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseDto;
import xyz.cursedman.gym_api.domain.entities.WorkoutSessionExercise;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkoutSessionExerciseMapper extends EntityMapper<WorkoutSessionExercise, WorkoutSessionExerciseDto> {
}
