package org.example.Services;

import org.example.Dto.DiscardItemDto;
import org.example.Handler.OutputHandler;
import org.example.Handler.ProtocolHandler;

import java.io.IOException;
import java.util.List;

import static org.example.Constants.CommandEnum.VIEW_DISCARD_ITEMS;

public class DiscardItemService {
    public List<DiscardItemDto> getDiscardItems(String sessionToken, ProtocolHandler protocolHandler) throws IOException {
        protocolHandler.sendRequest(VIEW_DISCARD_ITEMS.getCommandName(), "", sessionToken);
        List<DiscardItemDto> rolloutMenu = protocolHandler.receiveResponseList(DiscardItemDto.class);

        for (DiscardItemDto item : rolloutMenu) {
            OutputHandler.printDiscardItemResponse(item);
        }

        return rolloutMenu;
    }
}
