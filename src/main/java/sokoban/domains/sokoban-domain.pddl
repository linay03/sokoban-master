
(define (domain Sokoban)

    (:requirements :strips :typing)

    (:types
        position
        direction
    )

    (:predicates
        (adjacent ?from ?to - position ?d - direction)
        (clear ?pos - position)
        (guard-at ?pos - position)
        (box-at ?pos - position)
        (destination ?pos - position)
    )

    ;; Déplacement simple du joueur
    (:action move-player
        :parameters (?from ?to - position ?d - direction)

        :precondition (and
            (guard-at ?from)
            (adjacent ?from ?to ?d)
            (clear ?to)
        )

        :effect (and
            (not (guard-at ?from))
            (guard-at ?to)
            (clear ?from)
            (not (clear ?to))
        )
    )

    ;; Déplacement d'une boîte par le joueur
    (:action move-box
        :parameters (
            ?guard-from ?box-from ?box-to - position
            ?d-guard ?d-box - direction
        )

        :precondition (and
            (guard-at ?guard-from)
            (box-at ?box-from)
            (adjacent ?guard-from ?box-from ?d-guard)
            (adjacent ?box-from ?box-to ?d-box)
            (clear ?box-to)
        )

        :effect (and
            ;; Le joueur avance sur l'ancienne position de la boîte
            (not (guard-at ?guard-from))
            (guard-at ?box-from)

            ;; La boîte avance
            (not (box-at ?box-from))
            (box-at ?box-to)

            ;; Mise à jour des cases libres
            (clear ?guard-from)
            (not (clear ?box-to))
        )
    )
)
