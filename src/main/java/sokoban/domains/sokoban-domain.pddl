(define (domain Sokoban)
    (:requirements :strips :typing)
    (:types
        box
        guard
        position 
        direction
    )
    (:predicates
        (at-position ?g - guard ?pos - position)
        (at-box ?b - box ?pos - position)
        (destination ?pos - position)
        (adjacent ?from ?to - position) ;; True if position ?from is adjacent to position ?to
        (clear ?pos - position)
        (next-move ?from - position ?to - position ?d - direction)
    )

    (:action move-box
        :parameters (?b - box 
        ?g - guard 
        ?guard-pos - position 
        ?box-pos - position 
        ?to - position 
        ?dir - direction
        )

        :precondition (and
            (at-position ?g ?guard-pos)
            (at-box ?b ?box-pos)
            (adjacent ?guard-pos ?box-pos)
            (adjacent ?box-pos ?to)
            (next-move ?guard-pos ?box-pos ?dir) ;; Player is able to move the box in a certain direction
            (next-move ?box-pos ?to ?dir)
            (clear ?to)
        )

        :effect (and
            (not (at-position ?g ?guard-pos))
            (at-position ?g ?box-pos)
            (not (at-box ?b ?box-pos))
            (at-box ?b ?to)
            (clear ?box-pos)
            (not (clear ?to))
        )
    )

    (:action move-player
        :parameters (?g - guard ?from - position ?to - position ?d - direction)
        :precondition(and
            (at-position ?g ?from)
            (adjacent ?from ?to)
            (clear ?to)
            (next-move ?from ?to ?d)
        )

        :effect (and
            (not (at-position ?g ?from))
            (at-position ?g ?to)
        )
    )
)