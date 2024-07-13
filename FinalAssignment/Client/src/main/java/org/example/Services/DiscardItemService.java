package org.example.Services;

import org.example.Dto.DiscardItemDto;
import org.example.Handler.OutputHandler;
import org.example.Handler.RequestHandler;
import org.example.Handler.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class DiscardItemService {
    public List<DiscardItemDto> getDiscardItems(BufferedReader in, PrintWriter out, String sessionToken) throws IOException {
        RequestHandler.sendRequest(out, "VIEW_DISCARD_ITEMS", "", sessionToken);

        List<DiscardItemDto> rolloutMenu = ResponseHandler.readResponseList(in, DiscardItemDto.class);

        for (DiscardItemDto item : rolloutMenu) {
            OutputHandler.printDiscardItemResponse(item);
        }

        return rolloutMenu;
    }
}
