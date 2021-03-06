/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.pp.dsl.target;

import org.eclipse.emf.common.util.URI;

import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;
import com.puppetlabs.geppetto.semver.Version;

public enum PuppetTarget {
	// @fmtOff
	PUPPET26("2.6.18", "2.6", "puppet-2.6.18.pptp", null, null, ComplianceLevel.PUPPET_2_6),
	PUPPET27("2.7.26", "2.7", "puppet-2.7.26.pptp", null, null, ComplianceLevel.PUPPET_2_7),
	PUPPET30("3.0.2", "3.0", "puppet-3.0.2.pptp", null, null, ComplianceLevel.PUPPET_3_0),
	PUPPET32("3.2.4", "3.2", "puppet-3.2.4.pptp", null, null, ComplianceLevel.PUPPET_3_2),
	PUPPET34("3.4.3", "3.4", "puppet-3.4.3.pptp", null, null, ComplianceLevel.PUPPET_3_4),
	PUPPET35("3.5.1", "3.5", "puppet-3.5.1.pptp", null, null, ComplianceLevel.PUPPET_3_5),
	PUPPET36("3.6.2", "3.6", "puppet-3.6.2.pptp", null, null, ComplianceLevel.PUPPET_3_6),
	PUPPET37("3.7.3", "3.7", "puppet-3.7.3.pptp", null, null, ComplianceLevel.PUPPET_3_7),
	PUPPET40("3.7.3", "4.0", "puppet-3.7.3.pptp", "types-1.0.0.pptp", null, ComplianceLevel.PUPPET_4_0);
	// @fmtOn

	public static PuppetTarget forComplianceLevel(ComplianceLevel level, boolean enterprise) {
		for(PuppetTarget target : values())
			if(target.complianceLevel == level && enterprise == target.isPuppetEnterprise())
				return target;
		StringBuilder bld = new StringBuilder();
		bld.append("No ");
		if(enterprise)
			bld.append("enterprise ");
		bld.append("target found for compliance level ");
		bld.append(level);
		throw new IllegalArgumentException(bld.toString());
	}

	/**
	 * Returns the PuppetTarget with a literal equal to the argument
	 *
	 * @param literal
	 *            The argument to match
	 * @return The matching target
	 * @throws IllegalArgumentException
	 *             if no match is found
	 */
	public static PuppetTarget forLiteral(String literal) {
		if(literal != null) {
			for(PuppetTarget pt : values())
				if(literal.equals(pt.getLiteral()))
					return pt;
		}
		throw new IllegalArgumentException("No PuppetTarget matches literal '" + literal + '\'');
	}

	public static PuppetTarget getDefault() {
		return PUPPET37;
	}

	private final Version version;

	private final Version peVersion;

	private final String literal;

	private final ComplianceLevel complianceLevel;

	private final URI targetURI;

	private final URI typesURI;

	PuppetTarget(String version, String literal, String targetURI, String typesURI, String peVersion, ComplianceLevel complianceLevel) {
		this.version = Version.fromString(version);
		this.literal = literal;
		this.targetURI = PptpResourceUtil.getURI(targetURI);
		this.typesURI = typesURI == null
			? null
			: PptpResourceUtil.getURI(typesURI);
		this.peVersion = Version.fromString(peVersion);
		this.complianceLevel = complianceLevel;
	}

	public ComplianceLevel getComplianceLevel() {
		return complianceLevel;
	}

	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the Puppet Enterprise version if applicable.
	 *
	 * @return The Puppet Enterprise version or <code>null</code> if this target is not PE
	 */
	public Version getPEVersion() {
		return peVersion;
	}

	public URI getPlatformURI() {
		return targetURI;
	}

	public URI getTypesURI() {
		return typesURI;
	}

	public Version getVersion() {
		return version;
	}

	public boolean isPuppetEnterprise() {
		return peVersion != null;
	}
}
