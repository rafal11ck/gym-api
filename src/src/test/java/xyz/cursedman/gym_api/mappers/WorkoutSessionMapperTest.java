package xyz.cursedman.gym_api.mappers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionDto;
import xyz.cursedman.gym_api.domain.entities.Exercise;
import xyz.cursedman.gym_api.domain.entities.Hall;
import xyz.cursedman.gym_api.domain.entities.User;
import xyz.cursedman.gym_api.domain.entities.WorkoutSession;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class WorkoutSessionMapperTest {

	@Autowired
	private WorkoutSessionMapper workoutSessionMapper;

	@Test
	void test() {
		Set<User> attendants = new HashSet<>();
		attendants.add(new User());

		Set<Exercise> exercises = new HashSet<>();
		exercises.add(new Exercise());

		WorkoutSession session = WorkoutSession.builder()
			.uuid(UUID.randomUUID())
			.coach(new User())
			.hall(new Hall())
			.date(new Date())
			.title("title")
			.description("desc")
			.attendants(attendants)
			.exercises(exercises)
			.build();

		WorkoutSessionDto dto = workoutSessionMapper.toDtoFromEntity(session);
		assertSame(dto.getAttendants(), attendants);
	}
}
