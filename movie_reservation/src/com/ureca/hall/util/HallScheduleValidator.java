package com.ureca.hall.util;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.ureca.user.model.dto.Hall;
import com.ureca.user.model.dto.MovieException;

/**
 * 같은 관(hall_id)에서는 상영 시간대가 겹치면 안 된다.
 * 같은 영화의 다른 회차(시작만 다름)는 허용, 시간만 겹치지 않으면 된다.
 */
public final class HallScheduleValidator
{
	private static final int MINUTES_PER_DAY = 24 * 60;

	private HallScheduleValidator()
	{
	}

	public static void assertNoOverlap(Hall candidate, List<Hall> sameHallShows)
	{
		if (candidate.getStartTime() == null || candidate.getMovieTime() <= 0)
		{
			throw new MovieException("시작 시간과 상영 시간(분)을 올바르게 입력해 주세요.");
		}

		List<int[]> candidateSegments = toSegments(toMinutes(candidate.getStartTime()),
				toMinutes(candidate.getStartTime()) + candidate.getMovieTime());

		for (Hall other : sameHallShows)
		{
			if (other.getSeatId() == candidate.getSeatId())
			{
				continue;
			}
			if (other.getStartTime() == null || other.getMovieTime() <= 0)
			{
				continue;
			}

			int otherStart = toMinutes(other.getStartTime());
			List<int[]> otherSegments = toSegments(otherStart, otherStart + other.getMovieTime());
			if (segmentsOverlap(candidateSegments, otherSegments))
			{
				String sameMovie = candidate.getMovieId() != null && candidate.getMovieId().equals(other.getMovieId())
						? " (같은 영화라도 시간 중복 불가)"
						: "";
				throw new MovieException(String.format("%d관 상영 시간이 겹칩니다%s.%n기존: %s (%d분) / 신규: %s (%d분)",
						candidate.getHallId(), sameMovie, other.getStartTime(), other.getMovieTime(),
						candidate.getStartTime(), candidate.getMovieTime()));
			}
		}
	}

	private static int toMinutes(Time time)
	{
		java.time.LocalTime lt = time.toLocalTime();
		return lt.getHour() * 60 + lt.getMinute();
	}

	private static List<int[]> toSegments(int start, int endExclusive)
	{
		List<int[]> segments = new ArrayList<>(2);
		if (endExclusive <= MINUTES_PER_DAY)
		{
			segments.add(new int[] { start, endExclusive });
			return segments;
		}
		segments.add(new int[] { start, MINUTES_PER_DAY });
		segments.add(new int[] { 0, endExclusive - MINUTES_PER_DAY });
		return segments;
	}

	private static boolean segmentsOverlap(List<int[]> a, List<int[]> b)
	{
		for (int[] segA : a)
		{
			for (int[] segB : b)
			{
				if (segA[0] < segB[1] && segB[0] < segA[1])
				{
					return true;
				}
			}
		}
		return false;
	}
}
