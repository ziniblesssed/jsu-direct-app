package jsudirect;
public class DestinationData {
    public String origin;
    public String originNode;
    public String destination;
    public String destinationNode;
    public String directions;
    public String distance;
  

    public DestinationData() {
    }

    public DestinationData(String origin, String originNode, String destination, String destinationNode, String directions, String distance) {
        this.origin = origin;
        this.originNode = originNode;
        this.destination = destination;
        this.destinationNode = destinationNode;
        this.directions = directions;
        this.distance = distance;
    }
}
