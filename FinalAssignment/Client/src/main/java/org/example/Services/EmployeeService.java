package org.example.Services;

import com.google.gson.Gson;
import org.example.Dto.EmployeeMenuDto;
import org.example.Handler.OutputHandler;
import org.example.Handler.RequestHandler;
import org.example.Handler.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class EmployeeService {

    public List<EmployeeMenuDto> getRollOutMenu(BufferedReader in, PrintWriter out) throws IOException {
        RequestHandler.sendRequest(out, "GET_ROLL_OUT_MENU", "");

        List<EmployeeMenuDto> rolloutMenu = ResponseHandler.readResponseList(in, EmployeeMenuDto.class);

        for (EmployeeMenuDto item : rolloutMenu) {
            OutputHandler.printVotedItemResponse(item);
        }

        return rolloutMenu;
    }

    public String chooseFoodItem(BufferedReader in, PrintWriter out, List<Integer> chosenItems) throws IOException {
        Gson gson = new Gson();
        String chosenItemsPayload = gson.toJson(chosenItems);
        RequestHandler.sendRequest(out, "CHOOSE_ITEMS", chosenItemsPayload);

        String chooseItemResponse = ResponseHandler.readResponseObject(in, String.class);
        return chooseItemResponse;
    }

    public List<Integer> addChosenItemInList(int foodItemId, List<Integer> chosenItems) {
        chosenItems.add(foodItemId);
        return chosenItems;
    }
}
