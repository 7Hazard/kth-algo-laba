package uppgift4;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        var random = ThreadLocalRandom.current();

        var handledLandRequests = new LinkedList<Request>();
        var unhandledLandRequests = new LinkedList<Request>();

        var handledLiftoffRequests = new LinkedList<Request>();
        var unhandledLiftoffRequests = new LinkedList<Request>();

        Request currentRequest = null;
        var currentRequestKind = RequestKind.None;
        var currentRequestTimer = 0; // max 20, +5 each timestep

        long totalTime = 0; // total amount of time simulation executed in minutes
        var maxTime = 10 * 365 * 24 * 60; // only simulate for 10 years

        // each iteration is imagined to be a 5 minutes timestep
        while (totalTime != maxTime) {
            // process current request
            if (currentRequestKind != RequestKind.None) // request is being processed
            {
                currentRequestTimer += 5;

                // if request has been processed for 20 minutes
                if (currentRequestTimer == 20) {
                    // move to handled requests
                    if (currentRequestKind == RequestKind.Land)
                        handledLandRequests.addLast(currentRequest);
                    else
                        handledLiftoffRequests.addLast(currentRequest);

                    // end request
                    currentRequestTimer = 0;
                    currentRequestKind = RequestKind.None;
                }
            }

            // if no request if being handled
            if (currentRequestKind == RequestKind.None) {
                // handle another request
                if (!unhandledLandRequests.isEmpty()) // land before liftoff
                {
                    currentRequestKind = RequestKind.Land;
                    currentRequest = unhandledLandRequests.removeFirst();
                } else if (!unhandledLiftoffRequests.isEmpty()) {
                    currentRequestKind = RequestKind.Liftoff;
                    currentRequest = unhandledLiftoffRequests.removeFirst();
                }
            }

            // add waited time for all unhandled requests
            for (var r : unhandledLandRequests)
                r.timeWaited += 5;
            for (var r : unhandledLiftoffRequests)
                r.timeWaited += 5;

            // get chance of request
            if (random.nextFloat() <= 0.05f) // land 0-5%
            {
                unhandledLandRequests.add(new Request());
            }
            if (random.nextFloat() <= 0.05f) // liftoff 0-5%
            {
                unhandledLiftoffRequests.add(new Request());
            }

            // increment total timelapse
            totalTime += 5;
        }

        // process and output stats
        float totalLandWait = 0;
        float maxLandWait = 0;
        for (var r : handledLandRequests) {
            totalLandWait += r.timeWaited;
            if (maxLandWait < r.timeWaited)
                maxLandWait = r.timeWaited;
        }
        float avgLandWait = totalLandWait / handledLandRequests.size();
        System.out.println("Wait for landing: avg " + avgLandWait + ", max " + maxLandWait);

        float totalLiftoffWait = 0;
        float maxLiftoffWait = 0;
        for (var r : handledLiftoffRequests) {
            totalLiftoffWait += r.timeWaited;
            if (maxLiftoffWait < r.timeWaited)
                maxLiftoffWait = r.timeWaited;
        }
        float avgLiftoffWait = totalLiftoffWait / handledLiftoffRequests.size();
        System.out.println("Wait for liftoff: avg " + avgLiftoffWait + ", max " + maxLiftoffWait);
    }

    enum RequestKind {None, Land, Liftoff}

    static class Request {
        int timeWaited = 0; // minutes
    }
}
