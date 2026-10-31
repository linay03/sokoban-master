(define (domain Sokoban)
    (:requirements :strips :typing)
    (:types
        position
        direction
    )

    (:predicates
        (adjacent ?from ?to - position ?d - direction) ; True if position ?to is adjacent to position ?from
        (clear ?pos - position) ;; True if position ?pos is clear (no box or guard)
        (guard-at ?pos - position) ; True if guard is at ?pos
        (box-at ?pos - position) ; position of the box
        (destination ?pos - position)
    )

    ;; Player move to another cell
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

    ;; Box move from the player to another cell
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
            (not (guard-at ?guard-from))
            (guard-at ?box-from)
            (not (box-at ?box-from))
            (box-at ?box-to)
            (clear ?guard-from)
            (not (clear ?box-to))
        )
    )
)
