/**
 *  Copyright 2010 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Hospital-core module.
 *
 *  Hospital-core module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Hospital-core module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Hospital-core module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/


package org.openmrs.module.hospitalcore.concept;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.ConceptSet;
import org.openmrs.api.context.Context;

public class TestTree {

	private ConceptNode root;
	private boolean buildTreeSuccessful = false;
	private Set<Integer> conceptIDSet = new TreeSet<Integer>();
	private List<ConceptNode> flatList = new ArrayList<ConceptNode>();

	/**
	 * Constructor using a lab concept then building the tree of lab and its sub
	 * tests
	 * 
	 * @param labConcept
	 *            concept of the lab
	 */
	public TestTree(Concept labConcept) {
		root = new ConceptNode(labConcept);
		buildTestTree(root);
		buildTreeSuccessful = true;
		flatList = asList();
	}

	/**
	 * Constructor using a lab concept then building the tree of lab and its sub
	 * tests, it should find the concept using the concept name.
	 * 
	 * @param labName
	 *            concept name of the lab
	 */
	public TestTree(String labName) {
		Concept labConcept = Context.getConceptService().getConcept(labName);
		if (labConcept != null) {
			if ((labConcept.getConceptClass().getName().equals("Question"))
					&& (labConcept.getDatatype().getName().equals("Coded"))) {
				root = new ConceptNode(labConcept);
				buildTestTree(root);
				buildTreeSuccessful = true;
				flatList = asList();
			}
		}
	}

	/**
	 * Return true if build tree successfully, otherwise return false
	 * 
	 * @return
	 */
	public boolean buildTreeSuccessful() {
		return buildTreeSuccessful;
	}

	/**
	 * See whether a concept belongs to a lab.
	 * 
	 * @param concept
	 * @return
	 */
	public boolean contains(Concept concept) {
		return conceptIDSet.contains(concept.getId());
	}

	/**
	 * Get the root node of the test tree
	 * 
	 * @return
	 */
	public ConceptNode getRootLab() {
		return root;
	}

	/**
	 * Get the root node of test tree
	 * 
	 * @return
	 */
	public ConceptNode getRootNode() {
		return root;
	}

	/**
	 * Print to console the tree of test
	 */
	public void printTestTree() {
		if (buildTreeSuccessful) {
			printNode(root, 1);
		}
	}

	private void printNode(ConceptNode node, int level) {
		for (int i = 0; i < level; i++) {
			System.out.print("   ");
		}
		System.out.println(node.getConcept().getName().getName());
		for (ConceptNode child : node.getChildNodes()) {
			printNode(child, level + 1);
		}

	}

	private void buildTestTree(ConceptNode node) {
		if (!(node.getConcept().getConceptClass().getName().equals("Test"))
				|| (node.getConcept().getConceptClass().getName()
						.equals("LabSet"))) {
			for (ConceptAnswer ca : node.getConcept().getAnswers()) {
				Concept c = ca.getAnswerConcept();
				ConceptNode child = new ConceptNode(c, node);
				node.getChildNodes().add(child);
				conceptIDSet.add(c.getConceptId());
				buildTestTree(child);
			}

			for (ConceptSet cs : node.getConcept().getConceptSets()) {
				Concept c = cs.getConcept();
				ConceptNode child = new ConceptNode(c, node);
				node.getChildNodes().add(child);
				conceptIDSet.add(c.getConceptId());
				buildTestTree(child);
			}
		}
	}

	/**
	 * Get the concept id set
	 * 
	 * @return
	 */
	public Set<Integer> getConceptIDSet() {
		return conceptIDSet;
	}

	/**
	 * Convert the tree to a list of concept node
	 * 
	 * @return
	 */
	public List<ConceptNode> getTreeAsList() {
		return flatList;
	}

	private List<ConceptNode> asList() {
		List<ConceptNode> list = new ArrayList<ConceptNode>();
		Set<ConceptNode> set = new HashSet<ConceptNode>();
		convertToList(set, root);
		list.addAll(set);
		Collections.sort(list, new Comparator<ConceptNode>() {

			public int compare(ConceptNode o1, ConceptNode o2) {
				return o1.getConcept().getConceptId()
						- o2.getConcept().getConceptId();
			}

		});
		return list;
	}

	private void convertToList(Set<ConceptNode> set, ConceptNode node) {
		set.add(node);
		for (ConceptNode child : node.getChildNodes()) {
			convertToList(set, child);
		}
	}

	/**
	 * Find a node in tree
	 * 
	 * @param concept
	 * @return
	 */
	public ConceptNode findNode(Concept concept) {
		for (int i = 0; i < flatList.size(); i++) {
			ConceptNode node = flatList.get(i);
			if (node.getConcept().getConceptId().equals(concept.getConceptId()))
				return node;
		}
		return null;
	}

	/**
	 * Get the list of ConceptSet
	 * 
	 * @return
	 */
	public Set<Concept> getConceptSet() {
		Set<Concept> set = new HashSet<Concept>();
		for (ConceptNode node : flatList) {
			set.add(node.getConcept());
		}
		return set;
	}
}
