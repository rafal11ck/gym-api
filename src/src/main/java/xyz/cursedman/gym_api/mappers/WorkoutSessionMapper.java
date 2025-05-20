package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionDto;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionRequest;
import xyz.cursedman.gym_api.domain.entities.User;
import xyz.cursedman.gym_api.domain.entities.WorkoutSession;
import xyz.cursedman.gym_api.services.ExerciseService;
import xyz.cursedman.gym_api.services.HallService;
import xyz.cursedman.gym_api.services.UserService;
import xyz.cursedman.gym_api.services.WorkoutSessionExerciseService;

import java.util.Optional;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	uses = {HallService.class, UserService.class, ExerciseService.class, WorkoutSessionExerciseService.class}
)
public interface WorkoutSessionMapper extends EntityRequestMapper<
	WorkoutSession, WorkoutSessionDto, WorkoutSessionRequest
	> {
	@Override
	@Mapping(target = "hallUuid", source = "hall.uuid")
	@Mapping(target = "coachUuid", source = "coach.uuid")
	WorkoutSessionRequest toRequestFromEntity(WorkoutSession entity);

	@Override
	@Mapping(target = "hall", source = "hallUuid")
	@Mapping(target = "coach", source = "coachUuid")
	WorkoutSession toEntityFromRequest(WorkoutSessionRequest request);

	@Override
	@Mapping(target = "hall", source = "hallUuid")
	@Mapping(target = "coach", source = "coachUuid")
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromRequest(WorkoutSessionRequest request, @MappingTarget WorkoutSession entity);

	@Override
	@Mapping(target = "exercises", source = "uuid")
	WorkoutSessionDto toDto(Optional<User> entity);
}
