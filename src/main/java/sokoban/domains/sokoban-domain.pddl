(define (domain Sokoban)
    (:requirements :strips :typing)
    (:types
        box
        position 
        direction
    )
    (:predicates
        (adjacent ?from ?to - position ?d - direction) ; True if position ?to is adjacent to position ?from in direction ?d
        (clear ?pos - position) ;; True if position ?pos is clear (no box or guard)
        (guard-at ?pos - position) ; position of the guard
        (box-at ?pos - position); position of the box
    )

    (:action move-player
        :parameters (?from ?to - position ?d - direction)

        :precondition (and
            (guard-at ?from) ; there is a guard at the current position
            (adjacent ?from ?to ?d) ; the target position is adjacent to the current position in direction ?d
            (clear ?to) ; the target position is clear (no box or guard)
        )

        :effect (and
            (not (guard-at ?from)) ; remove the guard from the current position
            (box-at ?to) ; place the box at the new position
            (not (clear ?to)) ; mark the new position as not clear
            (guard-at ?to)
            )
    )

    (:action move-box
        :parameters (?guard-from ?box-from ?box-to - position ?d-guard ?d-box - direction)
        :precondition(and
            (adjacent ?guard-from ?box-from ?d-guard) ; the guard is adjacent to the box in direction ?d-guard
            (adjacent ?box-from ?box-to ?d-box) ; the box is adjacent to the target position in direction ?d-box
            (clear ?box-to) ; the target position for the box is clear
        )

        :effect (and
            (clear ?guard-from) ; mark the guard's previous position as clear
            (not (box-at ?box-from)) ; remove the box from its previous position
            (box-at ?box-to) ; move the box to the new position
        )
    )
)