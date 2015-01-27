/*
 * EveMinerals
 * Copyright (C) 2014  Rastislav Komara
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package komara.eo.impl.mineral;

import komara.eo.Sovereignty;
import komara.eo.mineral.*;

import java.util.*;

/**
 * Created by Rastislav Komara on 12/22/13.
 * Part of ${PROJECT_NAME} project.
 */
public class ReprocessingPlantImpl implements ReprocessingPlant {

    private EnumSet<Sovereignty> sovereignty = Sovereignty.all();
    private double securityStatus = -1.0D;
    private double yield = 1.0D;
    private double tax = 0.0D;

    public ReprocessingPlantImpl(EnumSet<Sovereignty> sovereignty, double securityStatus) {
        this.sovereignty = sovereignty;
        this.securityStatus = securityStatus;
    }

    public ReprocessingPlantImpl() {
    }

    public void setSovereignty(EnumSet<Sovereignty> sovereignty) {
        this.sovereignty = sovereignty;
    }

    public void setSecurityStatus(double securityStatus) {
        if (1.0 < securityStatus || securityStatus < -1.0) {
            throw new IllegalArgumentException("Expected security status is between 1.0 and -1.0");
        }
        this.securityStatus = securityStatus;
    }

    @Override
    public void setUserStatistics(double yield, double tax) {
        if (1.0 < yield || yield < 0.0) {
            throw new IllegalArgumentException("Yield should be between 1.0 and 0.0");
        }
        if (1.0 < tax || tax < 0.0) {
            throw new IllegalArgumentException("Tax should be between 1.0 and 0.0");
        }
        if (yield < tax) {
            throw new IllegalArgumentException("Actual yield is smaller than tax. Solution impossible.");
        }
        this.yield = yield;
        this.tax = tax;
    }

    public EnumSet<Sovereignty> getSovereignty() {
        return sovereignty;
    }

    public double getSecurityStatus() {
        return securityStatus;
    }

    public double getYield() {
        return yield;
    }

    public double getTax() {
        return tax;
    }

    @Override
    public ReprocessingSolution getMinimumOreVolume(long[] minerals) {
        Collection<Ore> ores = OreList.get().getOres(sovereignty, securityStatus);

        List<OreSolution> solutions = solveForMineral(new OreSolution(minerals), Mineral.getMostRare(), ores, yield, tax);
        System.out.println("Found solutions: ");
        for (OreSolution solution : solutions) {
            printSolution(solution);
        }
        OreSolution bestSolution = findBestSolution(solutions);

        System.out.println("Best solution: ");
        printSolution(bestSolution);

        return new ReprocessingSolutionImpl(bestSolution);
    }

    @Override
    public Collection<ReprocessingSolution> getAllSolutions(long[] minerals) {
        Collection<Ore> ores = OreList.get().getOres(sovereignty, securityStatus);

        List<OreSolution> solutions = solveForMineral(new OreSolution(minerals), Mineral.getMostRare(), ores, yield, tax);
        Set<ReprocessingSolution> result = new HashSet<ReprocessingSolution>(solutions.size());
        for (OreSolution solution : solutions) {
            result.add(new ReprocessingSolutionImpl(solution));
        }
        return result;
    }

    private void printSolution(OreSolution solution) {
        if (solution != null) {
            System.out.println("Solution:");
            for (Map.Entry<Ore, Long> entry : solution.volumes.entrySet()) {
                System.out.printf("\t%s - %d : (%f units)%n", entry.getKey().getName(), entry.getValue(), solution.units.get(entry.getKey()));
            }
            System.out.println("\tResult: " + Arrays.toString(solution.minerals));
        }
    }

    private OreSolution findBestSolution(List<OreSolution> solutions) {
        if (solutions.isEmpty()) {
            return null;
        }

        if (solutions.size() == 1) {
            return solutions.get(0);
        }

        OreSolution winner = null;
        double minVolume = Double.MAX_VALUE;

        for (OreSolution solution : solutions) {
            if (solutionIsValid(solution)) {
                Map<Ore, Long> volumes = solution.volumes;
                double currentVolume = 0.0;
                for (Map.Entry<Ore, Long> entry : volumes.entrySet()) {
                    currentVolume += entry.getValue();
                }
                if (currentVolume < minVolume) {
                    minVolume = currentVolume;
                    winner = solution;
                }
            }
        }

        return winner;
    }

    private boolean solutionIsValid(OreSolution solution) {
        for (long mineral : solution.minerals) {
            if (mineral > 0) {
                return false;
            }
        }
        return true;
    }

    private List<OreSolution> solveForMineral(OreSolution input, Mineral mineral, Collection<Ore> ores, double yield, double tax) {
        if (input.minerals[mineral.ordinal()] > 0) {
            List<OreSolution> solutions = new ArrayList<OreSolution>();
            for (Ore ore : ores) {
                if (ore.hasMineral(mineral)) {
                    OreSolution newOne = new OreSolution(input);
                    solutions.add(newOne);
                    advanceSolution(newOne, ore, mineral, yield, tax);
                    if (mineral != Mineral.Tritanium) {
                        Mineral nextOne = Mineral.values()[mineral.ordinal() - 1];
                        solutions.addAll(solveForMineral(newOne, nextOne, ores, yield, tax));
                    }
                }
            }
            return solutions;
        } else if (mineral != Mineral.Tritanium) {
            Mineral nextOne = Mineral.values()[mineral.ordinal() - 1];
            return solveForMineral(input, nextOne, ores, yield, tax);
        }
        return Collections.emptyList();
    }

    private void advanceSolution(OreSolution solution, Ore ore, Mineral mineral, double yield, double tax) {
        if (solution.minerals[mineral.ordinal()] <= 0) {
            // user don't asked to have this mineral or some other ore already fulfil request.
            return;
        }

        OreMineral base = ore.getExtraction(mineral);
        double adjustedBatch = netUnits(base.getUnitsPerBatch(), yield, tax);
        long required = solution.minerals[mineral.ordinal()];
        double batches = Math.ceil(required / adjustedBatch);
        double units = ore.getUnitsToRefine() * batches;
        long oreVolume = (long) Math.ceil(units * ore.getVolume());

        List<OreMineral> extracted = ore.getBatchResult();
        for (OreMineral oreMineral : extracted) {
            long netBatch = (long) Math.floor((batches * netUnits(oreMineral.getUnitsPerBatch(), yield, tax)));
            int mineralIndex = oreMineral.getMineral().ordinal();
            solution.minerals[mineralIndex] = solution.minerals[mineralIndex] - netBatch;
        }

        Map<Ore, Long> volumes = solution.volumes;
        if (volumes.containsKey(ore) ) {
            oreVolume += volumes.get(ore);
            units += solution.units.get(ore);
        }
        volumes.put(ore, oreVolume);
        solution.units.put(ore, units);
    }

    private double netUnits(int units, double yield, double tax) {
        double rawUnits = units * yield;
        return rawUnits - (rawUnits * tax);
    }
}
