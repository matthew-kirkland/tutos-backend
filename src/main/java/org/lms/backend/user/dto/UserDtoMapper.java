package org.lms.backend.user.dto;

import org.lms.backend.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    public UserDto.Details toDetailsDto(User user) {
        if (user == null) return null;
        return new UserDto.Details(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getNameFirst(),
                user.getNameLast(),
                user.getDob()
        );
    }

    public UserDto.Summary toSummaryDto(User user) {
        if (user == null) return null;
        return new UserDto.Summary(
                user.getUserId(),
                user.getEmail(),
                user.getPhone(),
                user.getNameFirst(),
                user.getNameLast()
        );
    }
}
