package subway.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Lines {
    private final List<Line> lines;

    public Lines(List<Line> lines) {
        this.lines = new ArrayList<>(lines);
    }

    public void deleteStation(Station station) {
        lines.forEach(line -> line.deleteSections(station));
    }

    public List<Path> findPath() {
        return lines.stream()
                .flatMap(line -> line.getSections().stream()
                        .map(Path::new))
                .collect(Collectors.toList());
    }

    public List<Station> getAllStations() {
        return lines.stream()
                .flatMap(line -> line.getAllStations().stream())
                .collect(Collectors.toList());
    }

    public List<Line> getLines() {
        return List.copyOf(lines);
    }
}
