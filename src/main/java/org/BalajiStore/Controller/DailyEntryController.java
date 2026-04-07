package org.BalajiStore.Controller;

import org.BalajiStore.Model.DailyEntry;
import org.BalajiStore.Service.DailyEntryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/entries")
public class DailyEntryController {

    private final DailyEntryService dailyEntryService;

    public DailyEntryController(DailyEntryService dailyEntryService) {
        this.dailyEntryService = dailyEntryService;
    }

    @PostMapping
    public DailyEntry saveEntry(@RequestBody DailyEntry entry) {

        return dailyEntryService.saveEntry(entry);
    }

    @GetMapping
    public List<DailyEntry> getEntries() {
        return dailyEntryService.getAllEntries();
    }
    @DeleteMapping("/{id}")
    public void deleteEntry(@PathVariable Long id) {
        dailyEntryService.deleteEntry(id);
    }

    @GetMapping("/by-date")
    public List<DailyEntry> getEntriesByDate(@RequestParam String date) {
        return dailyEntryService.getEntriesByDate(date);
    }
    @GetMapping("/all")
    public List<DailyEntry> getAllEntries() {
        return dailyEntryService.getAll();
    }

    @PutMapping("/{id}")
    public DailyEntry updateEntry(@PathVariable Long id,
                                  @RequestBody DailyEntry entry) {
        return dailyEntryService.updateEntry(id, entry);
    }
}