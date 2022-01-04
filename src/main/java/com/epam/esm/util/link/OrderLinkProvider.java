package com.epam.esm.util.link;

import com.epam.esm.controller.UserController;
import com.epam.esm.mapper.UserDtoResponseMapper;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.dto.UserResponseDto;
import com.epam.esm.util.ColumnName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class OrderLinkProvider extends AbstractLinkProvider<OrderDto> {
    private static final Class<UserController> CONTROLLER_CLASS = UserController.class;
    private final LinkProvider<UserResponseDto> userResponseDtoLinkProvider;
    private final LinkProvider<GiftCertificateDto> giftCertificateDtoLinkProvider;
    private final UserDtoResponseMapper userDtoResponseMapper;

    @Autowired
    public OrderLinkProvider(LinkProvider<UserResponseDto> userResponseDtoLinkProvider,
                             LinkProvider<GiftCertificateDto> giftCertificateDtoLinkProvider,
                             UserDtoResponseMapper userDtoResponseMapper) {
        this.userResponseDtoLinkProvider = userResponseDtoLinkProvider;
        this.giftCertificateDtoLinkProvider = giftCertificateDtoLinkProvider;
        this.userDtoResponseMapper = userDtoResponseMapper;
    }

    @Override
    public void provideLinks(OrderDto dto) {
        UserDto userDto = dto.getUser();
        dto.add(linkTo(CONTROLLER_CLASS)
                .slash(userDto.getId())
                .slash(ColumnName.ORDERS)
                .slash(dto.getId())
                .withRel(SELF_LINK));
        UserResponseDto userResponseDto = userDtoResponseMapper.mapToDto(userDto);
        userResponseDtoLinkProvider.provideLinks(userResponseDto);
        giftCertificateDtoLinkProvider.provideLinks(dto.getGiftCertificate());
    }
}
