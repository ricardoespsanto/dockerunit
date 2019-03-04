package com.github.qzagarese.dockerunit.discovery.consul;

import static com.github.qzagarese.dockerunit.discovery.consul.ConsulDescriptor.CONSUL_PORT;
import static com.github.qzagarese.dockerunit.discovery.consul.ConsulDiscoveryConfig.DOCKER_BRIDGE_IP_DEFAULT;
import static com.github.qzagarese.dockerunit.discovery.consul.ConsulDiscoveryConfig.DOCKER_BRIDGE_IP_PROPERTY;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Volume;
import com.github.qzagarese.dockerunit.annotation.ContainerBuilder;
import com.github.qzagarese.dockerunit.annotation.Image;
import com.github.qzagarese.dockerunit.annotation.Named;

@Named("registrator")
@Image("gliderlabs/registrator:v7")
public class RegistratorDescriptor {

	@ContainerBuilder
	public CreateContainerCmd config(CreateContainerCmd cmd) {
		String dockerBridgeIp = System.getProperty(DOCKER_BRIDGE_IP_PROPERTY, DOCKER_BRIDGE_IP_DEFAULT);
		return cmd.withBinds(new Bind("/var/run/docker.sock", new Volume("/tmp/docker.sock")))
				.withCmd("-ip=" + dockerBridgeIp, "-cleanup", "-internal", "consul://" + dockerBridgeIp + ":" + CONSUL_PORT);
	}
	
}
