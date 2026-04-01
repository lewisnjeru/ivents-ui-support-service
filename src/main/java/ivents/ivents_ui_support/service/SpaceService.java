package ivents.ivents_ui_support.service;

import ivents.ivents_ui_support.dto.data.SpaceData;
import ivents.ivents_ui_support.dto.data.UserData;
import ivents.ivents_ui_support.dto.request.CreateSpaceRequest;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.PaginationResponse;
import ivents.ivents_ui_support.dto.role_permission.UserDetailsData;
import ivents.ivents_ui_support.entity.Space;
import ivents.ivents_ui_support.repository.SpaceRepository;
import ivents.ivents_ui_support.specification.SpaceSpecification;
import ivents.ivents_ui_support.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpaceService {

    private final SpaceRepository spaceRepository;

    private final AuthUtil authUtil;

    public EntityResponse fetchSpaces(int page, int size, String name, String mode, String createdBy,
                                      Instant from, Instant to) {

        Pageable paging = PageRequest.of(page, size);

        Page<Space> spacesPage = spaceRepository.findAll(
                SpaceSpecification.filterSpaces(name, mode, createdBy, from, to),
                paging
        );

        if (spacesPage.isEmpty()) {
            return EntityResponse.builder()
                    .status(false)
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("No Records found.")
                    .build();
        }

        List<SpaceData> spacesDataList = spacesPage.getContent()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        PaginationResponse paginationResponse = PaginationResponse.builder()
                .currentPage(spacesPage.getNumber())
                .totalPages(spacesPage.getTotalPages())
                .totalItems(spacesPage.getTotalElements())
                .build();

        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Request processed successfully")
                .data(spacesDataList)
                .paginationResponse(paginationResponse)
                .build();
    }

    public EntityResponse createSpace(CreateSpaceRequest request) {
        Optional<UserDetailsData> userDataOpt = authUtil.getCurrentUser();
        String email = userDataOpt.map(UserDetailsData::getEmail).orElse("system");

        Space space = new Space();

        space.setName(request.getName());
        space.setDescription(request.getDescription());
        space.setMode(request.getMode());
        space.setCreatedOn(Instant.now());
        space.setCreatedBy(email);
        space.setModifiedOn(Instant.now());
        space.setModifiedBy(email);

        spaceRepository.save(space);

        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Request Processed Successfully")
                .build();
    }

    private SpaceData mapToDto(Space space) {
        return SpaceData.builder()
                .id(space.getId())
                .name(space.getName())
                .description(space.getDescription())
                .mode(space.getMode())
                .createdOn(space.getCreatedOn())
                .createdBy(space.getCreatedBy())
                .modifiedOn(space.getModifiedOn())
                .modifiedBy(space.getModifiedBy())
                .build();
    }
}