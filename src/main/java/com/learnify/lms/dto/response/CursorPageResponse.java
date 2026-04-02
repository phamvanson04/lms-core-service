package com.learnify.lms.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CursorPageResponse<T> {
  private List<T> data;
  private String nextCursor;
  private boolean hasNext;
}
