export function Badge(name, icon, description, enabled, requirements) {
    this.name = name;
    this.icon = icon;
    this.description = description;
    this.enabled = enabled;
    this.requirements = requirements;
}

export function createBadgeElement(badge) {
    var element = document.createElement("div");
    element.className = badge.enabled ? "badge" : "badge disabled";
    if (badge.icon) {
        var img = document.createElement("img");
        img.src = badge.icon;
        img.alt = badge.name;
        element.appendChild(img);
    }

    var text = document.createTextNode(badge.name);
    element.appendChild(text);
    var description = document.createTextNode(badge.description);
    element.appendChild(description);

    return element;
}

export function displayBadges(badges) {
    var container = document.getElementById("badges-container");
    badges.forEach(badge => {
        var badgeElement = createBadgeElement(badge);
        container.appendChild(badgeElement);
    });
}

