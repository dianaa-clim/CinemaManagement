package org.example.web.Controller;

import common.Room;
import org.example.server.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff/rooms")
public class StaffRoomsController {

    private final RoomService roomService;

    public StaffRoomsController(RoomService roomService) {
        this.roomService = roomService;
    }

    // ================= LIST =================
    @GetMapping
    public String rooms(Model model) {
        model.addAttribute("rooms", roomService.findAll());
        return "staff/rooms";
    }

    // ================= ADD FORM =================
    @GetMapping("/new")
    public String newRoom(Model model) {
        model.addAttribute("room", new Room());
        return "staff/room_new";
    }

    // ================= SAVE =================
    @PostMapping("/new")
    public String saveRoom(@ModelAttribute Room room) {
        roomService.addRoom(room);
        return "redirect:/staff/rooms";
    }

    // ================= DELETE =================
    @PostMapping("/{id}/delete")
    public String deleteRoom(@PathVariable int id) {
        if (roomService.canDelete(id)) {
            roomService.deleteRoom(id);
        }
        return "redirect:/staff/rooms";
    }
}
