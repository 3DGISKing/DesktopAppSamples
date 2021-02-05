/* Generated file, do not edit */

#ifndef CXXTEST_RUNNING
#define CXXTEST_RUNNING
#endif

#define _CXXTEST_HAVE_STD
#include <cxxtest/TestListener.h>
#include <cxxtest/TestTracker.h>
#include <cxxtest/TestRunner.h>
#include <cxxtest/RealDescriptions.h>
#include <cxxtest/TestMain.h>
#include <cxxtest/ErrorPrinter.h>

int main( int argc, char *argv[] ) {
 int status;
    CxxTest::ErrorPrinter tmp;
    CxxTest::RealWorldDescription::_worldName = "cxxtest";
    status = CxxTest::Main< CxxTest::ErrorPrinter >( tmp, argc, argv );
    return status;
}
bool suite_Assignment2Tests_init = false;
#include "tests.h"

static Assignment2Tests suite_Assignment2Tests;

static CxxTest::List Tests_Assignment2Tests = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_Assignment2Tests( "tests.h", 165, "Assignment2Tests", suite_Assignment2Tests, Tests_Assignment2Tests );

static class TestDescription_suite_Assignment2Tests_testIsDagEmptyGraph : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testIsDagEmptyGraph() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 169, "testIsDagEmptyGraph" ) {}
 void runTest() { suite_Assignment2Tests.testIsDagEmptyGraph(); }
} testDescription_suite_Assignment2Tests_testIsDagEmptyGraph;

static class TestDescription_suite_Assignment2Tests_testIsDagSingleVertex : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testIsDagSingleVertex() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 174, "testIsDagSingleVertex" ) {}
 void runTest() { suite_Assignment2Tests.testIsDagSingleVertex(); }
} testDescription_suite_Assignment2Tests_testIsDagSingleVertex;

static class TestDescription_suite_Assignment2Tests_testIsDagCompletelyDisconnected : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testIsDagCompletelyDisconnected() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 180, "testIsDagCompletelyDisconnected" ) {}
 void runTest() { suite_Assignment2Tests.testIsDagCompletelyDisconnected(); }
} testDescription_suite_Assignment2Tests_testIsDagCompletelyDisconnected;

static class TestDescription_suite_Assignment2Tests_testIsDagSimpleDag : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testIsDagSimpleDag() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 187, "testIsDagSimpleDag" ) {}
 void runTest() { suite_Assignment2Tests.testIsDagSimpleDag(); }
} testDescription_suite_Assignment2Tests_testIsDagSimpleDag;

static class TestDescription_suite_Assignment2Tests_testIsDagComplicatedDag : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testIsDagComplicatedDag() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 201, "testIsDagComplicatedDag" ) {}
 void runTest() { suite_Assignment2Tests.testIsDagComplicatedDag(); }
} testDescription_suite_Assignment2Tests_testIsDagComplicatedDag;

static class TestDescription_suite_Assignment2Tests_testIsDagDisconnectedDag : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testIsDagDisconnectedDag() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 213, "testIsDagDisconnectedDag" ) {}
 void runTest() { suite_Assignment2Tests.testIsDagDisconnectedDag(); }
} testDescription_suite_Assignment2Tests_testIsDagDisconnectedDag;

static class TestDescription_suite_Assignment2Tests_testIsDagSimpleNonDag : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testIsDagSimpleNonDag() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 236, "testIsDagSimpleNonDag" ) {}
 void runTest() { suite_Assignment2Tests.testIsDagSimpleNonDag(); }
} testDescription_suite_Assignment2Tests_testIsDagSimpleNonDag;

static class TestDescription_suite_Assignment2Tests_testIsDagBiggerNonDag : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testIsDagBiggerNonDag() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 249, "testIsDagBiggerNonDag" ) {}
 void runTest() { suite_Assignment2Tests.testIsDagBiggerNonDag(); }
} testDescription_suite_Assignment2Tests_testIsDagBiggerNonDag;

static class TestDescription_suite_Assignment2Tests_testIsDagDisconnectedNonDag : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testIsDagDisconnectedNonDag() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 290, "testIsDagDisconnectedNonDag" ) {}
 void runTest() { suite_Assignment2Tests.testIsDagDisconnectedNonDag(); }
} testDescription_suite_Assignment2Tests_testIsDagDisconnectedNonDag;

static class TestDescription_suite_Assignment2Tests_testTopologicalSortEmptyGraph : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testTopologicalSortEmptyGraph() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 308, "testTopologicalSortEmptyGraph" ) {}
 void runTest() { suite_Assignment2Tests.testTopologicalSortEmptyGraph(); }
} testDescription_suite_Assignment2Tests_testTopologicalSortEmptyGraph;

static class TestDescription_suite_Assignment2Tests_testTopologicalSortConnectedGraph : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testTopologicalSortConnectedGraph() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 314, "testTopologicalSortConnectedGraph" ) {}
 void runTest() { suite_Assignment2Tests.testTopologicalSortConnectedGraph(); }
} testDescription_suite_Assignment2Tests_testTopologicalSortConnectedGraph;

static class TestDescription_suite_Assignment2Tests_testTopologicalSortDisconnectedGraph : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testTopologicalSortDisconnectedGraph() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 333, "testTopologicalSortDisconnectedGraph" ) {}
 void runTest() { suite_Assignment2Tests.testTopologicalSortDisconnectedGraph(); }
} testDescription_suite_Assignment2Tests_testTopologicalSortDisconnectedGraph;

static class TestDescription_suite_Assignment2Tests_testIsHamiltonianDagEmptyGraph : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testIsHamiltonianDagEmptyGraph() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 364, "testIsHamiltonianDagEmptyGraph" ) {}
 void runTest() { suite_Assignment2Tests.testIsHamiltonianDagEmptyGraph(); }
} testDescription_suite_Assignment2Tests_testIsHamiltonianDagEmptyGraph;

static class TestDescription_suite_Assignment2Tests_testIsHamiltonianDagOneVertex : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testIsHamiltonianDagOneVertex() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 369, "testIsHamiltonianDagOneVertex" ) {}
 void runTest() { suite_Assignment2Tests.testIsHamiltonianDagOneVertex(); }
} testDescription_suite_Assignment2Tests_testIsHamiltonianDagOneVertex;

static class TestDescription_suite_Assignment2Tests_testIsHamiltonianDagPath : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testIsHamiltonianDagPath() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 375, "testIsHamiltonianDagPath" ) {}
 void runTest() { suite_Assignment2Tests.testIsHamiltonianDagPath(); }
} testDescription_suite_Assignment2Tests_testIsHamiltonianDagPath;

static class TestDescription_suite_Assignment2Tests_testIsHamiltonianDagWithPath : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testIsHamiltonianDagWithPath() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 392, "testIsHamiltonianDagWithPath" ) {}
 void runTest() { suite_Assignment2Tests.testIsHamiltonianDagWithPath(); }
} testDescription_suite_Assignment2Tests_testIsHamiltonianDagWithPath;

static class TestDescription_suite_Assignment2Tests_testIsHamiltonianDagNoPath : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testIsHamiltonianDagNoPath() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 415, "testIsHamiltonianDagNoPath" ) {}
 void runTest() { suite_Assignment2Tests.testIsHamiltonianDagNoPath(); }
} testDescription_suite_Assignment2Tests_testIsHamiltonianDagNoPath;

static class TestDescription_suite_Assignment2Tests_testComponentsEmptyGraph : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testComponentsEmptyGraph() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 458, "testComponentsEmptyGraph" ) {}
 void runTest() { suite_Assignment2Tests.testComponentsEmptyGraph(); }
} testDescription_suite_Assignment2Tests_testComponentsEmptyGraph;

static class TestDescription_suite_Assignment2Tests_testComponentsSingleVertex : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testComponentsSingleVertex() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 464, "testComponentsSingleVertex" ) {}
 void runTest() { suite_Assignment2Tests.testComponentsSingleVertex(); }
} testDescription_suite_Assignment2Tests_testComponentsSingleVertex;

static class TestDescription_suite_Assignment2Tests_testComponentsEdgelessGraph : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testComponentsEdgelessGraph() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 477, "testComponentsEdgelessGraph" ) {}
 void runTest() { suite_Assignment2Tests.testComponentsEdgelessGraph(); }
} testDescription_suite_Assignment2Tests_testComponentsEdgelessGraph;

static class TestDescription_suite_Assignment2Tests_testComponentsSingleDagComponent : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testComponentsSingleDagComponent() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 497, "testComponentsSingleDagComponent" ) {}
 void runTest() { suite_Assignment2Tests.testComponentsSingleDagComponent(); }
} testDescription_suite_Assignment2Tests_testComponentsSingleDagComponent;

static class TestDescription_suite_Assignment2Tests_testComponentsSingleStronglyConnectedComponent : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testComponentsSingleStronglyConnectedComponent() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 516, "testComponentsSingleStronglyConnectedComponent" ) {}
 void runTest() { suite_Assignment2Tests.testComponentsSingleStronglyConnectedComponent(); }
} testDescription_suite_Assignment2Tests_testComponentsSingleStronglyConnectedComponent;

static class TestDescription_suite_Assignment2Tests_testComponentsSeveralComponents : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testComponentsSeveralComponents() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 535, "testComponentsSeveralComponents" ) {}
 void runTest() { suite_Assignment2Tests.testComponentsSeveralComponents(); }
} testDescription_suite_Assignment2Tests_testComponentsSeveralComponents;

static class TestDescription_suite_Assignment2Tests_testSCCEmptyGraph : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testSCCEmptyGraph() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 577, "testSCCEmptyGraph" ) {}
 void runTest() { suite_Assignment2Tests.testSCCEmptyGraph(); }
} testDescription_suite_Assignment2Tests_testSCCEmptyGraph;

static class TestDescription_suite_Assignment2Tests_testSCCSingleVertex : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testSCCSingleVertex() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 583, "testSCCSingleVertex" ) {}
 void runTest() { suite_Assignment2Tests.testSCCSingleVertex(); }
} testDescription_suite_Assignment2Tests_testSCCSingleVertex;

static class TestDescription_suite_Assignment2Tests_testSCCEdgelessGraph : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testSCCEdgelessGraph() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 597, "testSCCEdgelessGraph" ) {}
 void runTest() { suite_Assignment2Tests.testSCCEdgelessGraph(); }
} testDescription_suite_Assignment2Tests_testSCCEdgelessGraph;

static class TestDescription_suite_Assignment2Tests_testSCCSingleDagComponent : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testSCCSingleDagComponent() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 617, "testSCCSingleDagComponent" ) {}
 void runTest() { suite_Assignment2Tests.testSCCSingleDagComponent(); }
} testDescription_suite_Assignment2Tests_testSCCSingleDagComponent;

static class TestDescription_suite_Assignment2Tests_testSCCSingleStronglyConnectedComponent : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testSCCSingleStronglyConnectedComponent() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 636, "testSCCSingleStronglyConnectedComponent" ) {}
 void runTest() { suite_Assignment2Tests.testSCCSingleStronglyConnectedComponent(); }
} testDescription_suite_Assignment2Tests_testSCCSingleStronglyConnectedComponent;

static class TestDescription_suite_Assignment2Tests_testSCCSeveralSCCs : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testSCCSeveralSCCs() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 652, "testSCCSeveralSCCs" ) {}
 void runTest() { suite_Assignment2Tests.testSCCSeveralSCCs(); }
} testDescription_suite_Assignment2Tests_testSCCSeveralSCCs;

static class TestDescription_suite_Assignment2Tests_testSCCSeveralDags : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testSCCSeveralDags() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 693, "testSCCSeveralDags" ) {}
 void runTest() { suite_Assignment2Tests.testSCCSeveralDags(); }
} testDescription_suite_Assignment2Tests_testSCCSeveralDags;

static class TestDescription_suite_Assignment2Tests_testSCCTreeOfSCC : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testSCCTreeOfSCC() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 726, "testSCCTreeOfSCC" ) {}
 void runTest() { suite_Assignment2Tests.testSCCTreeOfSCC(); }
} testDescription_suite_Assignment2Tests_testSCCTreeOfSCC;

static class TestDescription_suite_Assignment2Tests_testDistancesSingleVertex : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testDistancesSingleVertex() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 774, "testDistancesSingleVertex" ) {}
 void runTest() { suite_Assignment2Tests.testDistancesSingleVertex(); }
} testDescription_suite_Assignment2Tests_testDistancesSingleVertex;

static class TestDescription_suite_Assignment2Tests_testDistances : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_Assignment2Tests_testDistances() : CxxTest::RealTestDescription( Tests_Assignment2Tests, suiteDescription_Assignment2Tests, 784, "testDistances" ) {}
 void runTest() { suite_Assignment2Tests.testDistances(); }
} testDescription_suite_Assignment2Tests_testDistances;

#include <cxxtest/Root.cpp>
const char* CxxTest::RealWorldDescription::_worldName = "cxxtest";
