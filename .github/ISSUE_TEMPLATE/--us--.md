---
name: "[ US ]"
about: C'est le template de tout les US
title: ''
labels: ''
assignees: ''

---

name: User Story
description: Création d'une User Story standardisée
title: "[US] "
labels: ["user story"]
assignees: []

body:
  - type: input
    id: ref
    attributes:
      label: Ref
      description: Référence de la user story (ex: US01, US02…)
      placeholder: US01
    validations:
      required: true

  - type: input
    id: role
    attributes:
      label: En tant que
      description: Rôle de l'utilisateur
      placeholder: ex: secrétaire, coach, parent, joueur
    validations:
      required: true

  - type: textarea
    id: want
    attributes:
      label: Je veux…
      description: Action ou besoin exprimé
      placeholder: ex: créer un événement
    validations:
      required: true

  - type: textarea
    id: purpose
    attributes:
      label: Afin de…
      description: Objectif métier
      placeholder: ex: informer les joueurs
    validations:
      required: true

  - type: dropdown
    id: priority
    attributes:
      label: Priorité
      description: Niveau de priorité
      options:
        - 1 - Haute
        - 2 - Moyenne
        - 3 - Basse
    validations:
      required: true

  - type: input
    id: us_point
    attributes:
      label: US Point
      description: Charge estimée (story points)
      placeholder: ex: 1, 2, 3, 5
    validations:
      required: true
